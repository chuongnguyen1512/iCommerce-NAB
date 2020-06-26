package com.icommerce.nab.repository;

import com.icommerce.nab.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository or DAO class for {@link Product}
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Finding product info by product number
     *
     * @param prodNum product number
     * @return product object
     */
    @Query("SELECT p FROM Product p WHERE p.prodNum = :prodNum")
    Product findProductByProductNum(@Param("prodNum") String prodNum);

    /**
     * Finding existing products info by list of product numbers
     *
     * @param prodNums list of product numbers
     * @return collection of info included product number, product id
     */
    @Query("SELECT p.prodNum, p.id FROM Product p WHERE p.prodNum IN :prodNums")
    List<Object[]> findExistingProductsByProductNums(@Param("prodNums") List<String> prodNums);
}
