package com.icommerce.nab.repository;

import com.icommerce.nab.entity.order.Order;
import com.icommerce.nab.entity.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository or DAO class for {@link Order}
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find orders by list of order numbers and update status with given status
     *
     * @param orderStatus update status
     * @param orderNums list of query order numbers
     * @return updated orders number
     */
    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.status = :orderStatus WHERE o.orderNum IN :orderNums")
    int updateOrderStatus(@Param("orderStatus") OrderStatus orderStatus,
                            @Param("orderNums") List<String> orderNums);

    /**
     * Finding orders by status
     *
     * @param orderStatus status
     * @return list of orders
     */
    @Query("SELECT o FROM Order o WHERE o.status = :orderStatus")
    List<Order> findOrdersByStatus(@Param("orderStatus") OrderStatus orderStatus);
}
