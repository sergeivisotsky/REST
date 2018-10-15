package org.sergei.rest.service;

import org.junit.Ignore;
import org.junit.Test;
import org.sergei.rest.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@Ignore
public class CustomerServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private CustomerService customerService = new CustomerService();

    @Test
    public void findAllTest() {
        logger.info(Arrays.toString(new List[]{customerService.findAll()}));
    }

    @Test
    public void saveTest() {
        customerService.save(new Customer("John", "Smith", 25));
    }

}