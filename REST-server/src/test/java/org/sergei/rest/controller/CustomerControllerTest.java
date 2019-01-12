/*
 * Copyright 2018-2019 Sergei Visotsky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.rest.controller;

import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.rest.RestServerApplication;
import org.sergei.rest.model.Customer;
import org.sergei.rest.repository.CustomerRepository;
import org.sergei.rest.testconfig.ResourceServerConfiguration;
import org.sergei.rest.testconfig.WebSecurityConfigTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link CustomerController}
 *
 * @author Sergei Visotsky
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestServerApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
@ContextConfiguration(classes = {ResourceServerConfiguration.class, WebSecurityConfigTest.class})
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
                .andExpect(jsonPath("$[0].customerId").isNotEmpty())
                .andExpect(jsonPath("$[0].firstName").value(firstName))
                .andExpect(jsonPath("$[0].lastName").value(lastName))
                .andExpect(jsonPath("$[0].age").value(age));
        customerRepository.deleteAll();
    }

    @Test
    public void getCustomerById_thenReturnOk() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;

        Customer customer = setupCustomer(firstName, lastName, age);

        mvc.perform(
                get(BASE_URL + "/" + customer.getCustomerId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(firstName))
                .andExpect(jsonPath("$.lastName").value(lastName))
                .andExpect(jsonPath("$.age").value(age));
        customerRepository.deleteAll();
    }

    @Test
    public void postCustomer_thenReturnCreated() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;

        JSONObject jsonObject = new JSONObject()
                .put("firstName", firstName)
                .put("lastName", lastName)
                .put("age", age);

        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(firstName))
                .andExpect(jsonPath("$.lastName").value(lastName))
                .andExpect(jsonPath("$.age").value(age));
        customerRepository.deleteAll();
    }

    @Ignore
    @Test
    public void postCustomer_thenPutCustomer_thenGetOk() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;

        JSONObject jsonObject = new JSONObject()
                .put("firstName", firstName)
                .put("lastName", lastName)
                .put("age", age);
        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(firstName))
                .andExpect(jsonPath("$.lastName").value(lastName))
                .andExpect(jsonPath("$.age").value(age));

        final String putFirstName = "JohnP";
        final String putLastName = "SmithP";
        final int putAge = 21;

        JSONObject putJsonObject = new JSONObject()
                .put("firstName", putFirstName)
                .put("lastName", putLastName)
                .put("age", putAge);
        mvc.perform(
                put(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(putJsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(putFirstName))
                .andExpect(jsonPath("$.lastName").value(putLastName))
                .andExpect(jsonPath("$.age").value(putAge));
        customerRepository.deleteAll();
    }

    @Ignore
    @Test
    public void postCustomer_thenDelete_thenGetNoContent() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;

        JSONObject jsonObject = new JSONObject()
                .put("firstName", firstName)
                .put("lastName", lastName)
                .put("age", age);
        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(firstName))
                .andExpect(jsonPath("$.lastName").value(lastName))
                .andExpect(jsonPath("$.age").value(age));

        mvc.perform(delete(BASE_URL + "/1"))
                .andExpect(status().isNoContent());
        customerRepository.deleteAll();
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
