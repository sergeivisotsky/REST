package org.sergei.rest.dto;

import org.sergei.rest.model.Aircraft;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Sergei Visotsky, 2018
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AircraftDTO {

    @XmlElement
    private Long aircraftId;

    @XmlElement
    private String aircraftModel;

    @XmlElement
    private String aircraftName;

    @XmlElement
    private Double aircraftWeight;

    @XmlElement
    private Integer maxPassengers;

    public AircraftDTO() {
    }

    public AircraftDTO(Aircraft aircraft) {
        this.aircraftId = aircraft.getAircraftId();
        this.aircraftModel = aircraft.getAircraftModel();
        this.aircraftName = aircraft.getAircraftName();
        this.aircraftWeight = aircraft.getAircraftWeight();
        this.maxPassengers = aircraft.getMaxPassengers();
    }

    public Long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
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

    public Aircraft toModelObject() {
        Aircraft aircraft = new Aircraft();

        aircraft.setAircraftId(aircraftId);
        aircraft.setAircraftModel(aircraftModel);
        aircraft.setAircraftName(aircraftName);
        aircraft.setAircraftWeight(aircraftWeight);
        aircraft.setMaxPassengers(maxPassengers);

        return aircraft;
    }
}
