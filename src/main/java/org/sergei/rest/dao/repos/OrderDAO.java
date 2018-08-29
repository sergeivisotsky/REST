package org.sergei.rest.dao.repos;

import org.sergei.rest.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDAO {
    void save(Long customerId, Order order);

    List<Order> findAll();

    Order findById(Long id);

    Order findByCustomerIdAndOrderId(Long customerId, Long orderId);

    List<Order> findAllByCustomerIdAndGood(Long customerId, String good);

    List<Order> findAllByGood(String good);

    boolean existsById(Long orderId);

    boolean existsByGood(String good);

    boolean existsByCustomerId(Long customerId);

    void updateRecord(Long customerId, Long orderId, Order order);

    void delete(Order order);

    List<Order> findAllByCustomerId(Long id);
}
