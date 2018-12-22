package org.sergei.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "Order", description = "All order data")
public class OrderDTO {

    @ApiModelProperty("Order ID")
    private Long orderId;

    @ApiModelProperty("Customer ID who made an order")
    private Long customerId;

    @ApiModelProperty("Date when order was made")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    @ApiModelProperty("Date when order should be delivered")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date requiredDate;

    @ApiModelProperty("Date when order was delivered")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date shippedDate;
    private String status;

    @ApiModelProperty("List of order details")
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

    @ApiModelProperty(hidden = true)
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
