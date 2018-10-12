package org.sergei.rest.client.controller;

import org.sergei.rest.client.pojo.CustomerVO;
import org.sergei.rest.client.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String welcome(Model model) {
        final Long customerNumber = 1L;

        CustomerVO customerVO = customerService.getCustomerByNumber(customerNumber);
        model.addAttribute("customerNumber", customerVO.getCustomerNumber());
        model.addAttribute("firstName", customerVO.getFirstName());
        model.addAttribute("lastName", customerVO.getLastName());
        model.addAttribute("age", customerVO.getAge());

        return "index";
    }
}
