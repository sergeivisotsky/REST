/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class Welcome {

    @GetMapping
    @ResponseBody
    public String welcome() {
        return "REST";
    }

    @GetMapping("/docs")
    public String angularClient() {
        return "redirect:swagger-ui.html";
    }
}
