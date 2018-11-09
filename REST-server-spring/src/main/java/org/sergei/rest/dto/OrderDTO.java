/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(value = "Order", description = "All order data")
public class OrderDTO {

    @XmlElement
    private Long orderId;

    @XmlElement
    private Long customerId;

    @XmlElement
    @XmlSchemaType(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    @XmlElement
    @XmlSchemaType(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date requiredDate;

    @XmlElement
    @XmlSchemaType(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date shippedDate;

    @XmlElement
    private String status;

    @XmlElementWrapper(name = "orderDetails")
    @XmlElement(name = "orderDetailsInfo")
    @JsonProperty("orderDetails")
    private List<OrderDetailsDTO> orderDetailsDTO = new LinkedList<>();

    public OrderDTO() {
    }

    public OrderDTO(Long orderId, Long customerId,
                    Date orderDate, Date requiredDate, Date shippedDate,
                    String status, List<OrderDetailsDTO> orderDetailsDTO) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.status = status;
        this.orderDetailsDTO = orderDetailsDTO;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderDetailsDTO> getOrderDetailsDTO() {
        return orderDetailsDTO;
    }

    public void setOrderDetailsDTO(List<OrderDetailsDTO> orderDetailsDTO) {
        this.orderDetailsDTO = orderDetailsDTO;
    }
}
