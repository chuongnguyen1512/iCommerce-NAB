package com.icommerce.nab.deliver.service;

import com.icommerce.nab.dto.dto.OrderDTO;
import com.icommerce.nab.dto.dto.OrderItemDTO;
import com.icommerce.nab.dto.dto.PaymentTypeDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MockDataUtils {
    private MockDataUtils() {

    }

    public static List<OrderDTO> prepareOrderDTOs(int size) {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderNum(UUID.randomUUID().toString());
            orderDTO.setOrderDate(getDate());
            orderDTO.setContactPhone("123456789");
            orderDTO.setTotalPrice(10);
            orderDTO.setShippingAddress("Address " + i);
            orderDTO.setPaymentType(PaymentTypeDTO.CASH.getPaymentType());
            orderDTO.setUserName("Username " + i);

            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setQuantity(1);
            orderItemDTO.setPrice(10);
            orderItemDTO.setProductNumber("Product Num " + i);
            orderDTO.setItems(Collections.singletonList(orderItemDTO));
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    private static Date getDate() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse("2020-06-24");
        } catch (ParseException e) {
            // Do nothing
            return null;
        }
    }
}
