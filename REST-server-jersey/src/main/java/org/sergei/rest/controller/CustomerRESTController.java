package org.sergei.rest.controller;

import org.sergei.rest.dto.CustomerDTO;
import org.sergei.rest.service.CustomerService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.stream.Collectors;

@Path("/customers")
public class CustomerRESTController {

    @Inject
    private CustomerService customerService;

    @GET
    @Produces({"application/json", "application/xml"})
    public List<CustomerDTO> getAllCustomers() {
        return customerService.findAll().stream()
                .map(CustomerDTO::new)
                .collect(Collectors.toList());
    }

    // TODO: Find customer by ID

    @POST
    public void saveCustomer() {
        // TODO
    }

    // TODO: Update customer

    // TODO: Delete customer

    // TODO:
}
