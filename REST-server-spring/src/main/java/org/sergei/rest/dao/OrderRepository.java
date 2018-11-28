/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.rest.dao;

import org.sergei.rest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.customer.customerId = :customerId")
    List<Order> findAllByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT o FROM Order o INNER JOIN OrderDetails od ON o.orderId = od.order.orderId WHERE od.product.productCode = :productCode")
    List<Order> findAllByProductCode(@Param("productCode") String productCode);
}
