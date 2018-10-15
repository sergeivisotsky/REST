package org.sergei.rest.controller;

import org.sergei.rest.dto.CustomerDTO;
import org.sergei.rest.service.CustomerService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/customers")
public class CustomerRESTController {

    @Inject
    private CustomerService customerService;

    @GET
    @Produces({"application/json", "application/xml"})
    public Response getAllCustomers() {
        List<CustomerDTO> customerDTOList = customerService.findAll();
        GenericEntity genericEntity = new GenericEntity<List<CustomerDTO>>(customerDTOList){};
        return Response.ok(genericEntity).build();
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
