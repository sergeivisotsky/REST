package org.sergei.rest.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {

    @XmlElement
    private Long orderId;

    @XmlElement
    private Long customerId;

    @XmlElement
    private Long transId;

    @XmlElement
    private String good;

    @XmlElement
    private double goodWeight;

    @XmlElement
    private double price;

    public Order() {
    }

    public Order(Long transId, String good, double goodWeight, double price) {
        this.transId = transId;
        this.good = good;
        this.goodWeight = goodWeight;
        this.price = price;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public double getGoodWeight() {
        return goodWeight;
    }

    public void setGoodWeight(double goodWeight) {
        this.goodWeight = goodWeight;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
