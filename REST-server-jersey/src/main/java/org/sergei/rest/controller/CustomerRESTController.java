/*
 * Copyright (c) Sergei Visotsky, 2018
 */

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
    @Path("/{customerId}")
    public Response getCustomerById(@PathParam("customerId") Long customerId) {
        CustomerDTO customerDTO = customerService.findById(customerId);
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
    @Path("/{customerId}")
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    public Response updateCustomer(@PathParam("customerId") Long customerId, CustomerDTO customerDTO) {
        return Response.status(Response.Status.ACCEPTED)
                .entity(customerService.update(customerId, customerDTO))
                .build();
    }

    @DELETE
    @Path("/{customerId}")
    @Produces({"application/json", "application/xml"})
    public Response deleteCustomerById(@PathParam("customerId") Long customerId) {
        return Response.status(Response.Status.ACCEPTED)
                .entity(customerService.delete(customerId))
                .build();
    }
}
