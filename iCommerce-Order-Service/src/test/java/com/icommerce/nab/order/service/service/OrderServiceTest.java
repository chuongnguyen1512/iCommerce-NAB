package com.icommerce.nab.order.service.service;

import com.icommerce.nab.common.transform.OrderTransform;
import com.icommerce.nab.dto.restful.ICommerceResponse;
import com.icommerce.nab.order.service.MockDataUtils;
import com.icommerce.nab.repository.AccountRepository;
import com.icommerce.nab.repository.OrderRepository;
import com.icommerce.nab.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private OrderDeliverService orderDeliverService;

    @Mock
    private OrderPaymentService orderPaymentService;

    private OrderTransform orderTransform;

    private OrderService orderService;

    @Before
    public void init() {
        orderTransform = new OrderTransform();
        orderService = new OrderService(orderRepository, productRepository, accountRepository,
                orderDeliverService, orderPaymentService, orderTransform, 1);
    }

    @Test
    public void shouldProcessOrdersSuccessfully() {
        Mockito.when(accountRepository.findAccountByUsername(Mockito.anyString())).thenReturn(MockDataUtils.prepareAccount(1).get(0));
        Mockito.when(productRepository.findExistingProductsByProductNums(Mockito.anyList())).thenReturn(MockDataUtils.prepareExistingProductInfo(1));
        Mockito.when(productRepository.findProductByProductNum(Mockito.anyString())).thenReturn(MockDataUtils.prepareProduct(1).get(0));
        Mockito.when(orderRepository.saveAll(Mockito.anyList())).thenReturn(MockDataUtils.prepareOrders(1));
        Mockito.doNothing().when(orderDeliverService).sendDeliverOrders(Mockito.anyString(), Mockito.anyList());
        Mockito.doNothing().when(orderPaymentService).sendPaymentOrders(Mockito.anyString(), Mockito.anyList());

        ICommerceResponse response = orderService.processOrders(UUID.randomUUID().toString(), MockDataUtils.prepareOrderDTOs(1));

        Assert.assertNotNull(response);
        Assert.assertTrue(response.isSuccess());
        Mockito.verify(productRepository, Mockito.times(1)).findProductByProductNum(Mockito.anyString());
        Mockito.verify(orderRepository, Mockito.times(1)).saveAll(Mockito.anyList());
        Mockito.verify(orderRepository, Mockito.times(1)).saveAll(Mockito.anyList());
        Mockito.verify(orderDeliverService, Mockito.timeout(2000).times(1)).sendDeliverOrders(Mockito.anyString(), Mockito.anyList());
        Mockito.verify(orderPaymentService, Mockito.timeout(2000).times(1)).sendPaymentOrders(Mockito.anyString(), Mockito.anyList());
    }
}
