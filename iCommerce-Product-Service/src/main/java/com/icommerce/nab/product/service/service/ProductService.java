package com.icommerce.nab.product.service.service;

import com.icommerce.nab.common.transform.BaseTransform;
import com.icommerce.nab.common.transform.ProductTransform;
import com.icommerce.nab.dto.builder.RestfulResponseBuilder;
import com.icommerce.nab.dto.dto.ProductDTO;
import com.icommerce.nab.dto.restful.ICommerceResponse;
import com.icommerce.nab.entity.product.Product;
import com.icommerce.nab.repository.ProductRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.icommerce.nab.product.service.config.ApplicationConfig.BEAN_PRODUCT_TRANSFORM_SERVICE;

/**
 * Service class for handling process product data
 */
@Service
public class ProductService implements DefaultProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final BaseTransform<ProductDTO, Product> productTransform;

    @Autowired
    public ProductService(@Autowired ProductRepository productRepository,
                          @Qualifier(BEAN_PRODUCT_TRANSFORM_SERVICE) ProductTransform productTransform) {
        this.productRepository = productRepository;
        this.productTransform = productTransform;
    }

    /**
     * Saving products to database
     *
     * @param transactionId transaction id
     * @param productDTOs   list of product dtos
     * @return {@link ICommerceResponse} object
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public ICommerceResponse savingProducts(String transactionId, List<ProductDTO> productDTOs) {
        if (CollectionUtils.isEmpty(productDTOs)) {
            return RestfulResponseBuilder.buildErrorResponse(transactionId, "Products are empty or null", null);
        }

        for (ProductDTO product : productDTOs) {
            String errorMsg = verifyProduct(product);
            if (StringUtils.isNotBlank(errorMsg)) {

                LOGGER.warn("Request contained data is not valid: {}", errorMsg);
                return RestfulResponseBuilder.buildErrorResponse(transactionId, errorMsg, Arrays.asList(product));
            }
        }

        try {
            List<Product> products = productDTOs.stream().map(x -> productTransform.transform(x)).collect(Collectors.toList());

            // Find update products
            findAndUpdateProductIds(products);

            productRepository.saveAll(products);
            List<ProductDTO> savedProductDTOs = products.stream().map(x -> productTransform.transformBack(x)).collect(Collectors.toList());

            return RestfulResponseBuilder.buildSuccessResponse(transactionId, savedProductDTOs);
        } catch (Exception e) {
            String errorMsg = "Failed to saving products. Please contact administrator for further support";
            LOGGER.error(errorMsg, e);
            return RestfulResponseBuilder.buildErrorResponse(transactionId, errorMsg, productDTOs);
        }
    }

    /**
     * Finding existing products by product numbers and getting ids to update input products
     *
     * @param products input products
     */
    private void findAndUpdateProductIds(List<Product> products) {
        if (CollectionUtils.isEmpty(products)) {
            return;
        }

        List<String> productNums = products.stream().map(Product::getProdNum).collect(Collectors.toList());
        List<Object[]> updateProducts = productRepository.findExistingProductsByProductNums(productNums);

        if (CollectionUtils.isEmpty(updateProducts)) {
            return;
        }

        Map<String, Long> updateProductMap = new HashMap<>();
        updateProducts.forEach(x -> updateProductMap.put(String.valueOf(x[0]), Long.parseLong(String.valueOf(x[1]))));

        for (Product product : products) {
            Long updateId = updateProductMap.get(product.getProdNum());
            if (Objects.isNull(updateId) || updateId.longValue() <= 0) {
                continue;
            }
            product.setId(updateId);
        }
    }

    /**
     * Verify product data is valid or not
     *
     * @param product product data
     * @return error message
     */
    private String verifyProduct(ProductDTO product) {
        StringBuilder stringBuilder = new StringBuilder();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(product);

        String delimiterCharacter = ",";
        violations.stream().filter(x -> StringUtils.isNotBlank(x.getMessage())).forEach(x -> stringBuilder.append(x.getMessage()).append(delimiterCharacter + " "));
        String trimMsg = StringUtils.trim(stringBuilder.toString());
        return trimMsg.endsWith(delimiterCharacter) ? StringUtils.substring(trimMsg, 0, trimMsg.length() - 1) : trimMsg;
    }
}
