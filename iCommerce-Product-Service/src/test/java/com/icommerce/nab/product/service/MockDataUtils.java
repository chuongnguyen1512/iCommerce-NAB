package com.icommerce.nab.product.service;

import com.icommerce.nab.dto.dto.ProductDTO;
import com.icommerce.nab.dto.dto.ProductStatusDTO;
import com.icommerce.nab.entity.product.Product;
import com.icommerce.nab.entity.product.ProductStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockDataUtils {
    private MockDataUtils() {

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

    public static List<ProductDTO> prepareProductDTOs(int size) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProdName("Product " + i);
            productDTO.setProdNum(UUID.randomUUID().toString());
            productDTO.setProdDescript("Prod descript " + 1);
            productDTO.setStatus(ProductStatusDTO.NEW.getStatus());
            productDTO.setSupplierName("Supplier " + i);
            productDTO.setPrice(10);
            productDTOs.add(productDTO);
        }
        return productDTOs;
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
