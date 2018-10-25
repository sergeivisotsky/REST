package org.sergei.rest.client.controller;

import org.sergei.rest.client.pojo.CustomerVO;
import org.sergei.rest.client.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String welcome(Model model) {
        final Long customerId = 2L;

        CustomerVO customerVO = customerService.getCustomerByNumber(customerId);
        model.addAttribute("customerId", customerVO.getCustomerId());
        model.addAttribute("firstName", customerVO.getFirstName());
        model.addAttribute("lastName", customerVO.getLastName());
        model.addAttribute("age", customerVO.getAge());

        return "index";
    }

    @GetMapping("/angular")
    public String angularClient() {
        return "redirect:html/angular-client.html";
    }

    @GetMapping("/jquery")
    public String jqueryClient() {
        return "redirect:html/jquery-client.html";
    }
}
