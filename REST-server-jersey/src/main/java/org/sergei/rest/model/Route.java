package org.sergei.rest.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "route")
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "gen", sequenceName = "route_seq", allocationSize = 1)
    @Column(name = "route_id")
    private Long routeId;

    @Column(name = "route_distance")
    private Float distance;

    @Column(name = "departure_time")
    private Time departureTime;

    @Column(name = "arrival_time")
    private Time arrivalTime;

    @Column(name = "price")
    private Float price;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "route")
    private List<Aircraft> aircrafts;

    public Route() {
    }

    public Route(Long routeId, Float distance, Time departureTime, Time arrivalTime, Float price, List<Aircraft> aircrafts) {
        this.routeId = routeId;
        this.distance = distance;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.aircrafts = aircrafts;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeNumber) {
        this.routeId = routeNumber;
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

    public List<Aircraft> getAircrafts() {
        return aircrafts;
    }

    public void setAircrafts(List<Aircraft> aircraft) {
        this.aircrafts = aircraft;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route = (Route) o;
        return Objects.equals(getRouteId(), route.getRouteId()) &&
                Objects.equals(getDistance(), route.getDistance()) &&
                Objects.equals(getDepartureTime(), route.getDepartureTime()) &&
                Objects.equals(getArrivalTime(), route.getArrivalTime()) &&
                Objects.equals(getPrice(), route.getPrice()) &&
                Objects.equals(getAircrafts(), route.getAircrafts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRouteId(), getDistance(), getDepartureTime(), getArrivalTime(), getPrice(), getAircrafts());
    }
}
