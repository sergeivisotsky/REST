package org.sergei.rest.welcome;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
@RequestMapping
public class Welcome {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String welcome() {
        return "REST";
    }
}
