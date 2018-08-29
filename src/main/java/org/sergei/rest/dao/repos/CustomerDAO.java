package org.sergei.rest.dao.repos;

import org.sergei.rest.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDAO {
    void save(Customer customer);

    List<Customer> findAll();

    Customer findById(Long id);

    boolean existsById(Long customerId);

    void updateRecord(Customer customer);

    void delete(Customer customer);
}
