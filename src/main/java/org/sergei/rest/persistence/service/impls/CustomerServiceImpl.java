package org.sergei.rest.persistence.service.impls;

import org.sergei.rest.persistence.dao.repos.CustomerDAO;
import org.sergei.rest.persistence.pojo.Customer;
import org.sergei.rest.persistence.service.repos.CustomerService;
import org.sergei.rest.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    @Override
    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        if (!customerDAO.existsById(customerId)) {
            throw new RecordNotFoundException("No record with this parameters found");
        }

        return customerDAO.findById(customerId);
    }

    @Override
    public void saveCustomer(Customer customer) {
        customerDAO.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        if (!customerDAO.existsById(id)) {
            throw new RecordNotFoundException("Record with this parameters not found");
        }
        customerDAO.updateRecord(customer);
        return customer;
    }

    @Override
    public ResponseEntity<Customer> deleteCustomerById(Long id) {
        Customer customer = customerDAO.findById(id);
        if (!customerDAO.existsById(id)) {
            throw new RecordNotFoundException("Record with this parameters not found");
        }
        customerDAO.delete(customer);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
