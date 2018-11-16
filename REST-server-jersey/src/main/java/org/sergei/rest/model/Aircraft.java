package org.sergei.rest.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Sergei Visotsky, 2018
 */
@Entity
@Table(name = "aircraft")
public class Aircraft implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aircraft_seq")
    @SequenceGenerator(name = "aircraft_seq", sequenceName = "aircraft_seq", allocationSize = 1)
    @Column(name = "aircraft_id")
    private Long aircraftId;

    @Column(name = "aircraft_model", nullable = false)
    private String aircraftModel;

    @Column(name = "aircraft_name", nullable = false)
    private String aircraftName;

    @Column(name = "aircraft_weight", nullable = false)
    private Double aircraftWeight;

    @Column(name = "max_passengers", nullable = false)
    private Integer maxPassengers;

    public Aircraft() {
    }

    public Aircraft(Long aircraftId, String aircraftModel,
                    String aircraftName, Double aircraftWeight, Integer maxPassengers) {
        this.aircraftId = aircraftId;
        this.aircraftModel = aircraftModel;
        this.aircraftName = aircraftName;
        this.aircraftWeight = aircraftWeight;
        this.maxPassengers = maxPassengers;
    }

    public Long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Long aircraftNumber) {
        this.aircraftId = aircraftNumber;
    }

    public String getAircraftModel() {
        return aircraftModel;
    }

    public void setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
    }

    public String getAircraftName() {
        return aircraftName;
    }

    public void setAircraftName(String aircraftName) {
        this.aircraftName = aircraftName;
    }

    public Double getAircraftWeight() {
        return aircraftWeight;
    }

    public void setAircraftWeight(Double aircraftWeight) {
        this.aircraftWeight = aircraftWeight;
    }

    public Integer getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(Integer maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aircraft)) return false;
        Aircraft aircraft = (Aircraft) o;
        return Objects.equals(getAircraftId(), aircraft.getAircraftId()) &&
                Objects.equals(getAircraftModel(), aircraft.getAircraftModel()) &&
                Objects.equals(getAircraftName(), aircraft.getAircraftName()) &&
                Objects.equals(getAircraftWeight(), aircraft.getAircraftWeight()) &&
                Objects.equals(getMaxPassengers(), aircraft.getMaxPassengers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAircraftId(), getAircraftModel(), getAircraftName(), getAircraftWeight(), getMaxPassengers());
    }
}
