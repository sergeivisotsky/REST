package org.sergei.rest.repository;

import org.sergei.rest.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    @Query(value = "SELECT * FROM order_details WHERE order_number = :orderNumber", nativeQuery = true)
    List<OrderDetails> findAllByOrderNumber(@Param("orderNumber") Long orderNumber);
}
