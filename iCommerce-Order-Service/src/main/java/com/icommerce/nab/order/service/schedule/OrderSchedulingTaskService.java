package com.icommerce.nab.order.service.schedule;

import com.icommerce.nab.common.transform.BaseTransform;
import com.icommerce.nab.dto.dto.OrderDTO;
import com.icommerce.nab.entity.order.Order;
import com.icommerce.nab.entity.order.OrderStatus;
import com.icommerce.nab.order.service.service.OrderDeliverService;
import com.icommerce.nab.repository.OrderRepository;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.icommerce.nab.order.service.config.ApplicationConfig.BEAN_ORDER_TRANSFORM_SERVICE;

/**
 * Interface class for executing scheduling task for order-service
 */
@Service
public class OrderSchedulingTaskService implements SchedulingTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSchedulingTaskService.class);

    private final BaseTransform<OrderDTO, Order> orderTransform;
    private final OrderDeliverService orderDeliverService;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderSchedulingTaskService(@Autowired OrderRepository orderRepository,
                                      @Qualifier(BEAN_ORDER_TRANSFORM_SERVICE) BaseTransform<OrderDTO, Order> orderTransform,
                                      @Autowired OrderDeliverService orderDeliverService) {
        this.orderRepository = orderRepository;
        this.orderDeliverService = orderDeliverService;
        this.orderTransform = orderTransform;
    }

    /**
     * Executing schedule task
     */
    @Override
    @Scheduled(fixedDelayString = "${scheduler.task.cron}")
    public void scheduleTask() {
        LOGGER.info("Scheduling task has been triggered ...");

        //TODO: Replace with paging mechanism to prevent OOM issue
        List<Order> orders = orderRepository.findOrdersByStatus(OrderStatus.PENDING);
        if (CollectionUtils.isEmpty(orders)) {
            return;
        }

        List<OrderDTO> orderDTOs = orders.stream().map(x -> orderTransform.transformBack(x)).collect(Collectors.toList());
        orderDeliverService.sendDeliverOrders(UUID.randomUUID().toString(), orderDTOs);
    }
}
