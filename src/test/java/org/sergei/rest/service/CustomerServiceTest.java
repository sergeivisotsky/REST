package org.sergei.rest.service;


import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.model.Customer;
import org.sergei.rest.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:../../../../../../main/webapp/WEB-INF/applicationContext.xml")
public class CustomerServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void deleteCustomerByNumberTest() {
        Long customerNumber = 200L;
        Customer customer = customerRepository.findById(customerNumber)
                .orElseThrow(() -> new RecordNotFoundException("Customer with this number not found"));
        customerRepository.delete(customer);
        Assert.assertNull(customerRepository.findById(customerNumber));
    }
}