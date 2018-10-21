package org.sergei.rest.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sergei.rest.dto.RouteDTO;
import org.sergei.rest.model.Route;

@Mapper
public interface RouteMapper {
    @Mapping(source = "aircraft", target = "aircraftDTO")
    RouteDTO routeToRouteDTO(Route route);

    Route routeDTOtoRoute(RouteDTO routeDTO);
}
