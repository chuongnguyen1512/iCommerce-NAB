package com.icommerce.nab.common.transform;

import com.icommerce.nab.dto.dto.OrderDTO;
import com.icommerce.nab.dto.dto.OrderItemDTO;
import com.icommerce.nab.dto.dto.OrderStatusDTO;
import com.icommerce.nab.entity.order.LineItem;
import com.icommerce.nab.entity.order.Order;
import com.icommerce.nab.entity.order.OrderStatus;
import com.icommerce.nab.entity.payment.PaymentType;
import com.icommerce.nab.entity.product.Product;
import com.icommerce.nab.entity.user.Account;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Transformer class used to convert objects {@link OrderDTO}, {@link Order}
 */
public class OrderTransform extends BaseTransform<OrderDTO, Order> {

    /**
     * Deprecating for order transform
     *
     * @param orderDTO
     * @return
     */
    @Deprecated
    @Override
    public Order transform(OrderDTO orderDTO) {
        return null;
    }

    /**
     * Transform from {@link OrderDTO} to {@link Order}
     *
     * @param orderDTO dto object for order
     * @return order object
     */
    public Order transform(OrderDTO orderDTO, OrderStatusDTO orderStatusDTO, LookupOrderData lookupData) {
        if (Objects.isNull(orderDTO)) {
            return null;
        }
        Order order = new Order();
        order.setOrderNum(StringUtils.isEmpty(orderDTO.getOrderNum()) ? UUID.randomUUID().toString() : orderDTO.getOrderNum());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setContactPhone(orderDTO.getContactPhone());
        order.setStatus(OrderStatus.getOrderStatus(orderStatusDTO.status));
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setPaymentType(PaymentType.getPaymentType(orderDTO.getPaymentType()));
        if (!StringUtils.isEmpty(orderDTO.getUserName())) {
            order.setAccount(lookupData.lookupAccount(orderDTO.getUserName()));
        }
        Set<LineItem> lineItems = orderDTO.getItems().stream().map(x -> {
            LineItem lineItem = new LineItem();
            lineItem.setQuantity(x.getQuantity());
            lineItem.setTotalPrice(x.getPrice());
            if (!StringUtils.isEmpty(x.getProductNumber())) {
                lineItem.setProduct(lookupData.lookupProduct(x.getProductNumber()));
            }
            return lineItem;
        }).collect(Collectors.toSet());
        order.setItems(lineItems);
        return order;
    }

    /**
     * Transform from {@link Order} to {@link OrderDTO}
     *
     * @param order order object
     * @return dto object for order
     */
    @Override
    public OrderDTO transformBack(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderNum(order.getOrderNum());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setContactPhone(order.getContactPhone());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setShippingAddress(order.getShippingAddress());
        orderDTO.setPaymentType(order.getPaymentType().paymentType);
        orderDTO.setUserName(Objects.nonNull(order.getAccount()) ? order.getAccount().getUserName() : null);
        List<OrderItemDTO> orderItemDTOS = order.getItems().stream().map(x -> {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setQuantity(x.getQuantity());
            orderItemDTO.setPrice(x.getTotalPrice());
            orderItemDTO.setProductNumber(Objects.nonNull(x.getProduct()) ? x.getProduct().getProdNum() : null);
            return orderItemDTO;
        }).collect(Collectors.toList());
        orderDTO.setItems(orderItemDTOS);
        return orderDTO;
    }

    public interface LookupOrderData {
        Account lookupAccount(String username);
        Product lookupProduct(String prodNum);
    }
}
