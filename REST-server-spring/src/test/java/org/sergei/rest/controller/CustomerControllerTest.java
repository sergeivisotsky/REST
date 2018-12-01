/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.rest.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sergei.rest.dto.CustomerDTO;
import org.sergei.rest.service.CustomerService;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@Ignore
//@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    private CustomerDTO customerDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerDTO = new CustomerDTO();

        customerDTO.setCustomerId(1L);
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Smith");
        customerDTO.setAge(23);
    }

    @Test
    public void getAllCustomers() {
//        when(customerService.findOne(anyLong())).thenReturn(customerDTO);
    }

    @Test
    public void getCustomerById() {
    }

    @Test
    public void addCustomer() {
    }

    @Test
    public void updateRecord() {
    }

    @Test
    public void deleteCustomerById() {
    }
}