package org.sergei.rest.controller;

import org.sergei.rest.dto.AircraftDTO;
import org.sergei.rest.service.AircraftService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/aircraft")
public class AircraftRESTController {

    @Inject
    private AircraftService aircraftService;

    @GET
    @Produces({"application/json", "application/xml"})
    public Response getAllAircraft() {
        List<AircraftDTO> aircraftDTOList = aircraftService.findAll();
        GenericEntity genericEntity = new GenericEntity<List<AircraftDTO>>(aircraftDTOList) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Produces({"application/json", "application/xml"})
    @Path("/{aircraftId}")
    public Response getAircraftById(@PathParam("aircraftId") Long aircraftId) {
        AircraftDTO aircraftDTO = aircraftService.findById(aircraftId);
        return Response.ok(aircraftDTO).build();
    }

    @POST
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    public Response saveAircraft(AircraftDTO aircraftDTO) {
        return Response.status(Response.Status.CREATED)
                .entity(aircraftService.save(aircraftDTO))
                .build();
    }

    @PUT
    @Path("/{aircraftId}")
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    public Response updateAircraft(@PathParam("aircraftId") Long aircraftID, AircraftDTO aircraftDTO) {
        return Response.status(Response.Status.ACCEPTED)
                .entity(aircraftService.update(aircraftID, aircraftDTO))
                .build();
    }

    @DELETE
    @Path("/{aircraftId}")
    @Produces({"application/json", "application/xml"})
    public Response deleteCustomerById(@PathParam("aircraftId") Long aircraftId) {
        return Response.status(Response.Status.ACCEPTED)
                .entity(aircraftService.delete(aircraftId))
                .build();
    }
}
