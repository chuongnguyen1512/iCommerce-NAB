package com.icommerce.nab.deliver.service.service;

import com.icommerce.nab.deliver.service.MockDataUtils;
import com.icommerce.nab.dto.restful.ICommerceResponse;
import com.icommerce.nab.entity.order.OrderStatus;
import com.icommerce.nab.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class DeliverServiceTest {

    @Mock
    private OrderRepository orderRepository;

    private DeliverService deliverService;

    @Before
    public void init() {
        deliverService = new DeliverService(orderRepository);
    }

    @Test
    public void shouldDeliverOrdersSuccessfully() {
        Mockito.when(orderRepository.updateOrderStatus(Mockito.any(OrderStatus.class), Mockito.anyList())).thenReturn(1L);

        ICommerceResponse response = deliverService.deliverOrders(UUID.randomUUID().toString(), MockDataUtils.prepareOrderDTOs(1));

        Assert.assertNotNull(response);
        Assert.assertTrue(response.isSuccess());
        Mockito.verify(orderRepository, Mockito.times(1)).updateOrderStatus(Mockito.any(OrderStatus.class), Mockito.anyList());
    }
}
