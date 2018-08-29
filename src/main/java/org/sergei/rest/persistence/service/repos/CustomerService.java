package org.sergei.rest.persistence.service.repos;

import org.sergei.rest.persistence.pojo.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

    void saveCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer customer);

    ResponseEntity<Customer> deleteCustomerById(Long id);
}
