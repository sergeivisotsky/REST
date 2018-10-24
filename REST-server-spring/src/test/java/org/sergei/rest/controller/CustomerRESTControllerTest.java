package org.sergei.rest.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sergei.rest.dto.CustomerDTO;
import org.sergei.rest.service.CustomerService;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

//@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerRESTControllerTest {

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
        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);
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