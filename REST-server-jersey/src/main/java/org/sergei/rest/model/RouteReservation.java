package org.sergei.rest.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "route_reservation")
public class RouteReservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_seq")
    @SequenceGenerator(name = "reservation_seq", sequenceName = "reservation_seq", allocationSize = 1)
    @Column(name = "aircraft_id")
    private Long aircraftId;

    @Column(name = "reservation_date", nullable = false)
    private LocalDateTime reservationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "customer_fk"))
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", foreignKey = @ForeignKey(name = "route_fk"))
    private Route route;

    public RouteReservation() {
    }

    public RouteReservation(LocalDateTime reservationDate, Customer customer, Route route) {
        this.reservationDate = reservationDate;
        this.customer = customer;
        this.route = route;
    }

    public Long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RouteReservation)) return false;
        RouteReservation that = (RouteReservation) o;
        return Objects.equals(getAircraftId(), that.getAircraftId()) &&
                Objects.equals(getReservationDate(), that.getReservationDate()) &&
                Objects.equals(getCustomer(), that.getCustomer()) &&
                Objects.equals(getRoute(), that.getRoute());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAircraftId(), getReservationDate(), getCustomer(), getRoute());
    }
}
