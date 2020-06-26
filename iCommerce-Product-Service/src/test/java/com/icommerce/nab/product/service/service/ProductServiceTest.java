package com.icommerce.nab.product.service.service;

import com.icommerce.nab.common.transform.ProductTransform;
import com.icommerce.nab.dto.dto.ProductDTO;
import com.icommerce.nab.dto.restful.ICommerceResponse;
import com.icommerce.nab.product.service.MockDataUtils;
import com.icommerce.nab.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductTransform productTransform;

    private ProductService productService;

    @Before
    public void init() {
        productTransform = new ProductTransform();
        productService = new ProductService(productRepository, productTransform);
    }

    @Test
    public void shouldSavingProductsSuccessfully() {
        Mockito.when(productRepository.findExistingProductsByProductNums(Mockito.anyList())).thenReturn(MockDataUtils.prepareExistingProductInfo(1));
        Mockito.when(productRepository.saveAll(Mockito.anyList())).thenReturn(MockDataUtils.prepareProduct(1));

        List<ProductDTO> productDTOS = MockDataUtils.prepareProductDTOs(1);
        ICommerceResponse response = productService.savingProducts(UUID.randomUUID().toString(), productDTOS);

        Assert.assertNotNull(response);
        Assert.assertTrue(response.isSuccess());
        Mockito.verify(productRepository, Mockito.times(1)).findExistingProductsByProductNums(Mockito.anyList());
        Mockito.verify(productRepository, Mockito.times(1)).saveAll(Mockito.anyList());
    }
}
