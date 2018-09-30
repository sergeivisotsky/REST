package org.sergei.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderDTO {

    @XmlElement
    private Long orderNumber;

    @XmlElement
    @XmlSchemaType(name="date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date orderDate;

    @XmlElement
    @XmlSchemaType(name="date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date requiredDate;

    @XmlElement
    @XmlSchemaType(name="date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date shippedDate;

    @XmlElement
    private String status;

    private List<OrderDetailsDTO> orderDetailsDTO = new LinkedList<>();

    public OrderDTO() {
    }

    public OrderDTO(Long orderNumber, Date orderDate, Date requiredDate, Date shippedDate,
                    String status, List<OrderDetailsDTO> orderDetailsDTO) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.status = status;
        this.orderDetailsDTO = orderDetailsDTO;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
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