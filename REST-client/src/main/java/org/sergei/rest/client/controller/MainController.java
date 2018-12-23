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

    @Autowired
    private CustomerService customerService;

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

    @GetMapping("/angular")
    public String angularClient() {
        return "redirect:templates/angular-client.html";
    }

    @GetMapping("/jquery")
    public String jqueryClient() {
        return "redirect:templates/jquery-client.html";
    }
}
