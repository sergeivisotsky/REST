package org.sergei.rest.dto;

import org.sergei.rest.model.Route;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Time;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RouteDTO {

    @XmlElement
    private Long routeId;

    @XmlElement
    private Float distance;

    @XmlElement
    private Time departureTime;

    @XmlElement
    private Time arrivalTime;

    @XmlElement
    private Float price;

    @XmlElement
    private AircraftDTO aircraftDTO;

    public RouteDTO() {
    }

    public RouteDTO(Route route) {
        this.routeId = route.getRouteId();
        this.distance = route.getDistance();
        this.departureTime = route.getDepartureTime();
        this.arrivalTime = route.getArrivalTime();
        this.price = route.getPrice();
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public AircraftDTO getAircraftDTO() {
        return aircraftDTO;
    }

    public void setAircraftDTO(AircraftDTO aircraftDTO) {
        this.aircraftDTO = aircraftDTO;
    }

    public Route toModelObject() {
        Route route = new Route();

        route.setRouteId(routeId);
        route.setDistance(distance);
        route.setDepartureTime(departureTime);
        route.setArrivalTime(arrivalTime);
        route.setPrice(price);

        return route;
    }
}
