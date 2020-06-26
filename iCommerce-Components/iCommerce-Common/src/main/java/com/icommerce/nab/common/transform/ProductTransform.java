package com.icommerce.nab.common.transform;

import com.icommerce.nab.dto.dto.ProductDTO;
import com.icommerce.nab.dto.dto.ProductStatusDTO;
import com.icommerce.nab.entity.product.Product;
import com.icommerce.nab.entity.product.ProductStatus;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.UUID;

/**
 * Transformer class used to convert objects {@link ProductDTO}, {@link Product}
 */
public class ProductTransform extends BaseTransform<ProductDTO, Product> {

    /**
     * Transform from {@link ProductDTO} to {@link Product}
     *
     * @param productDTO dto object for product
     * @return product object
     */
    @Override
    public Product transform(ProductDTO productDTO) {
        if (Objects.isNull(productDTO)) {
            return null;
        }
        Product product = new Product();
        product.setProdNum(StringUtils.isEmpty(productDTO.getProdNum()) ? UUID.randomUUID().toString() : productDTO.getProdNum());
        product.setPrice(productDTO.getPrice());
        product.setProdName(productDTO.getProdName());
        product.setStatus(StringUtils.isEmpty(productDTO.getStatus()) ? ProductStatus.NEW : ProductStatus.getProductStatus(productDTO.getStatus()));
        product.setSupplierName(productDTO.getSupplierName());
        product.setProdDescript(productDTO.getProdDescript());
        return product;
    }

    /**
     * Transform from {@link Product} to {@link ProductDTO}
     *
     * @param product product object
     * @return dto object for product
     */
    @Override
    public ProductDTO transformBack(Product product) {
        if (Objects.isNull(product)) {
            return null;
        }
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(product.getPrice());
        productDTO.setProdName(product.getProdName());
        productDTO.setSupplierName(product.getSupplierName());
        productDTO.setStatus(Objects.nonNull(product.getStatus()) ? ProductStatusDTO.getProductStatusDTO(product.getStatus().status).getStatus() : null);
        productDTO.setProdDescript(product.getProdDescript());
        productDTO.setProdNum(product.getProdNum());
        return productDTO;
    }
}
