package org.sergei.rest.controller;

import org.sergei.rest.dto.CustomerDTO;
import org.sergei.rest.service.CustomerService;

import javax.inject.Inject;
import javax.ws.rs.*;
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
        GenericEntity genericEntity = new GenericEntity<List<CustomerDTO>>(customerDTOList) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Produces({"application/json", "application/xml"})
    @Path("/{customerNumber}")
    public Response getCustomerByNumber(@PathParam("customerNumber") Long customerNumber) {
        CustomerDTO customerDTO = customerService.findByCustomerNumber(customerNumber);
        return Response.ok(customerDTO).build();
    }

    @POST
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    public Response saveCustomer(CustomerDTO customerDTO) {
        return Response.status(Response.Status.CREATED)
                .entity(customerService.save(customerDTO))
                .build();
    }

    @PUT
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    public Response updateCustomer(CustomerDTO customerDTO) {
        return Response.status(Response.Status.ACCEPTED)
                .entity(customerService.update(customerDTO))
                .build();
    }

    // TODO: Delete customer

    // TODO:
}
