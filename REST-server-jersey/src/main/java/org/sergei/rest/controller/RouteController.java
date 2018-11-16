package org.sergei.rest.controller;

import org.sergei.rest.dto.RouteDTO;
import org.sergei.rest.service.RouteService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Path("/routes")
public class RouteController {

    @Inject
    private RouteService routeService;

    @GET
    @Produces({"application/json", "application/xml"})
    public Response findAll() {
        List<RouteDTO> routeDTOList = routeService.findAll();
        GenericEntity genericEntity = new GenericEntity<List<RouteDTO>>(routeDTOList) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Produces({"application/json", "application/xml"})
    @Path("/{routeId}")
    public Response getRouteById(@PathParam("routeId") Long routeId) {
        RouteDTO routeDTO = routeService.findById(routeId);
        return Response.ok(routeDTO).build();
    }

    @POST
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    public Response saveRoute(RouteDTO routeDTO) {
        return Response.status(Response.Status.CREATED)
                .entity(routeService.save(routeDTO))
                .build();
    }

    @PUT
    @Path("/{routeId}")
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    public Response updateRoute(@PathParam("routeId") Long routeId, RouteDTO routeDTO) {
        return Response.status(Response.Status.ACCEPTED)
                .entity(routeService.update(routeId, routeDTO))
                .build();
    }

    @DELETE
    @Path("/{routeId}")
    @Produces({"application/json", "application/xml"})
    public Response deleteRouteById(@PathParam("routeId") Long routeId) {
        return Response.status(Response.Status.NO_CONTENT)
                .entity(routeService.delete(routeId))
                .build();
    }
}
