package com.icommerce.nab.product.service.controller;

import com.icommerce.nab.common.controller.BaseController;
import com.icommerce.nab.dto.dto.ProductDTO;
import com.icommerce.nab.product.service.service.DefaultProductService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Restful controller for handling product http request
 */
@RestController
public class ProductController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private DefaultProductService productService;

    @Autowired
    public ProductController(@Autowired DefaultProductService productService) {
        this.productService = productService;
    }

    /**
     * Processing restful requests for url '/savingProducts'
     *
     * @param transactionId transaction id
     * @param productDTOs   list of product dtos
     * @return response entity object
     */
    @RequestMapping(value = "/savingProducts", method = RequestMethod.POST)
    public ResponseEntity savingProducts(@RequestHeader(name = "transaction_id") String transactionId,
                                         @RequestBody List<ProductDTO> productDTOs) {

        return processRestfulRequest(transactionId, new BaseController.ProcessRequestConsumer() {
            @Override
            public boolean isInvalidRequest() {
                return CollectionUtils.isEmpty(productDTOs);
            }

            @Override
            public Object executeRequest() {
                LOGGER.info("Receiving saving products request with products size: {}", productDTOs.size());
                return productService.savingProducts(transactionId, productDTOs);
            }
        });
    }
}
