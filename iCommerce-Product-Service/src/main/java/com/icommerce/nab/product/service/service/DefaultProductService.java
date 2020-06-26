package com.icommerce.nab.product.service.service;

import com.icommerce.nab.dto.dto.ProductDTO;
import com.icommerce.nab.dto.restful.ICommerceResponse;

import java.util.List;

/**
 * Interface class for handling product data
 */
public interface DefaultProductService {

    /**
     * Saving products to database
     *
     * @param transactionId transaction id
     * @param data          list of product dtos
     * @return {@link ICommerceResponse} object
     */
    ICommerceResponse savingProducts(String transactionId, List<ProductDTO> data);
}
