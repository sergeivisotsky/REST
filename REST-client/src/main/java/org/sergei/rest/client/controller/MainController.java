/*
 * Copyright 2018-2019 the original author.
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

package org.sergei.rest.client.controller;

import org.sergei.rest.client.model.Customer;
import org.sergei.rest.client.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

/**
 * @author Sergei Visotsky
 */
@Controller
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private final CustomerService customerService;

    @Autowired
    public MainController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public String welcome(Model model) {
        final Long customerId = 2L;

        ResponseEntity<Customer> customer = customerService.getCustomerByNumber(customerId);
        model.addAttribute("customerId", Objects.requireNonNull(customer.getBody()).getCustomerId());

        LOGGER.debug("Customer ID is: {}", customer.getBody().getCustomerId());

        model.addAttribute("firstName", customer.getBody().getFirstName());
        model.addAttribute("lastName", customer.getBody().getLastName());
        model.addAttribute("age", customer.getBody().getAge());

        return "index";
    }
}
