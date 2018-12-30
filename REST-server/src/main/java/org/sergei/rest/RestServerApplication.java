package org.sergei.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sergei Visotsky, 2018
 */
@SpringBootApplication
public class RestServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestServerApplication.class, args);
    }

    @RestController
    class WelcomeController {
        @GetMapping("/")
        public String welcome() {
            return "Flights";
        }

        @GetMapping("/docs")
        public void docsRedirect(HttpServletResponse response) throws IOException {
            response.sendRedirect("swagger-ui.html");
        }
    }
}
