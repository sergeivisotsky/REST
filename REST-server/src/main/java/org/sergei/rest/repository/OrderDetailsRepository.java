package org.sergei.rest.repository;

import org.sergei.rest.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    @Query("SELECT o FROM OrderDetails o WHERE o.order.orderId = :orderId")
    List<OrderDetails> findAllByOrderId(@Param("orderId") Long orderId);
}
