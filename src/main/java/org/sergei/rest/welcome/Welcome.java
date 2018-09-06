package org.sergei.rest.welcome;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
@RequestMapping("/")
public class Welcome {

    @GetMapping
    @ResponseBody
    public String welcome() {
        return "REST";
    }
}
