/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author Sergei Visotsky, 2018
 */
@ApiIgnore
@Controller
public class Welcome {

    @GetMapping("/")
    @ResponseBody
    public String welcome() {
        return "REST";
    }

    @GetMapping("/docs")
    public String docsRedirect() {
        return "redirect:swagger-ui.html";
    }
}
