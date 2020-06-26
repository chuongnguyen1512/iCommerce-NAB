package com.icommerce.nab.order.service;

import com.icommerce.nab.dto.dto.OrderDTO;
import com.icommerce.nab.dto.dto.OrderItemDTO;
import com.icommerce.nab.dto.dto.PaymentTypeDTO;
import com.icommerce.nab.entity.order.Order;
import com.icommerce.nab.entity.payment.PaymentType;
import com.icommerce.nab.entity.product.Product;
import com.icommerce.nab.entity.product.ProductStatus;
import com.icommerce.nab.entity.user.Account;

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

    public static List<Product> prepareProduct(int size) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Product product = new Product();
            product.setProdName("Product " + i);
            product.setProdNum(UUID.randomUUID().toString());
            product.setId(i);
            product.setProdDescript("Prod descript " + 1);
            product.setStatus(ProductStatus.NEW);
            product.setSupplierName("Supplier " + i);
            product.setPrice(10);
            products.add(product);
        }
        return products;
    }

    public static List<Order> prepareOrders(int size) {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Order order = new Order();
            order.setOrderNum(UUID.randomUUID().toString());
            order.setOrderDate(getDate());
            order.setContactPhone("123456789");
            order.setTotalPrice(10);
            order.setShippingAddress("Address " + i);
            order.setPaymentType(PaymentType.CASH);
            order.setId(1);
            orders.add(order);
        }
        return orders;
    }

    public static List<Account> prepareAccount(int size) {
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Account account = new Account();
            account.setId(1);
            account.setUserName("Username " + i);
            account.setClosed(false);
            account.setStartDate(getDate());
            account.setEmailAddress("Email " + i);
            accounts.add(account);
        }
        return accounts;
    }

    public static List<Object[]> prepareExistingProductInfo(int size) {
        List<Object[]> products = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Object[] product = new Object[2];
            product[0] = UUID.randomUUID().toString();
            product[1] = 1L;
            products.add(product);
        }
        return products;
    }
}
