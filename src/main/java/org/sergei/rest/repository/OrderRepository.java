package org.sergei.rest.repository;

import org.sergei.rest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders WHERE customer_number = :customerNumber", nativeQuery = true)
    List<Order> findAllByCustomerNumber(@Param("customerNumber") Long customerNumber);

    @Query(value = "SELECT * FROM orders INNER JOIN order_details o on orders.order_number = o.order_number WHERE product_code = :productCode", nativeQuery = true)
    List<Order> findAllByProductCode(@Param("productCode") String productCode);

//    @Query(value = "UPDATE rest_services.orders SET ", nativeQuery = true)
//    void update(Order order);
}
