package org.sergei.rest.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.rest.RestServerApplication;
import org.sergei.rest.model.Customer;
import org.sergei.rest.repository.CustomerRepository;
import org.sergei.rest.testconfig.ResourceServerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link CustomerController}
 *
 * @author Sergei Visotsky
 * Created on 12/7/2018
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestServerApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ResourceServerConfiguration.class})
@EnableJpaRepositories(basePackages = "org.sergei.rest.repository")
@EntityScan(basePackages = "org.sergei.rest.model")
public class CustomerControllerTest {

    private static final String BASE_URL = "/api/v1/customers";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void getAllCustomers_thenReturnOk() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;

        setupCustomer(firstName, lastName, age);

        mvc.perform(
                get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(firstName))
                .andExpect(jsonPath("$.lastName").value(lastName))
                .andExpect(jsonPath("$.age").value(age));
    }

    private Customer setupCustomer(String firstName, String lastName, int age) {
        Customer customer = new Customer();

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAge(age);

        customerRepository.save(customer);

        return customer;
    }
}
