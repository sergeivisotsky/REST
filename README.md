# REST
REST API implementation using Spring Boot

### Spring annotation depending on version
| Http method | Spring 4.xx | Spring 5.xx |
| ----------- | ----------- | ------------|
| GET | @RequestMapping(value = "/url",  method = RequestMethod.GET) | @GetMapping("/url") |
| POST | @RequestMapping(value = "/url",  method = RequestMethod.POST) | @PostMapping("/url") |
| PUT | @RequestMapping(value = "/url",  method = RequestMethod.PUT) | @PutMapping("/url") |
| DELETE | @RequestMapping(value = "/url",  method = RequestMethod.DELETE) | @DeleteMapping("/url") |
| PATCH | @RequestMapping(value = "/url",  method = RequestMethod.PATCH) | @PatchMapping("/url") |

One more thing to memorize is if that it is required to produce media type it should be done like that

| Spring 4.xx | Spring 5.xx |
| ----------- | ------------|
| @RequestMapping(value = "/url",  method = RequestMethod.GET, produces = {"application/json", "application/xml"}) | @GetMapping("/url", produces = {"application/json", "application/xml"}) |

NOTE: `@RequestMapping` annotation by default is GET method by itself so that it may be written in this way `@RequestMapping("/url")`.

### @Controller and @RestController annotation
Before Spring 5.xx the way web declare controller to be restful was annotating it li regular controller `@Controller` and in each method was required to write `@ResponseBody`

#### Spring 4.xx 
```java
@Controller
@RequestMapping(value = "/v1/customers", produces = {"application/json", "application/xml"})
public class CustomerRESTController {
    @RequestMapping(value = "/{customerId}",  method = RequestMethod.GET, 
    produces = {"application/json", "application/xml"})
    public @ResponseBody Customer getCustomerById(@PathVariable("customerId") Long customerId) {
        return customerService.getCustomerById(customerId);
    }
}
```

Starting from Spring 5.xx annotation `@RestController` appeared which includes `@ResponseBody ` by default so that it is not required to write it.

#### Spring 5.xx
```java
@RestController
@RequestMapping(value = "/v1/customers", produces = {"application/json", "application/xml"})
public class CustomerRESTController {
    @GetMapping(value = "/{customerId}", produces = {"application/json", "application/xml"})
    public Customer getCustomerById(@PathVariable("customerId") Long customerId) {
        return customerService.getCustomerById(customerId);
    }
}
```

To document this API Swagger documentation auto-generation was used which is available by this url: [http://localhost:8080/rest/api/swagger-ui.html](http://localhost:8080/rest/api/swagger-ui.html)