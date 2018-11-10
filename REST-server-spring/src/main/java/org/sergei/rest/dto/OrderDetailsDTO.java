/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

/**
 * @author Sergei Visotsky, 2018
 */
@ApiModel(value = "OrderDetails", description = "All oder details data")
@JsonRootName("orderDetails")
@XmlRootElement(name = "orderDetails")
@XmlAccessorType(XmlAccessType.FIELD)

public class OrderDetailsDTO {

    @XmlElement
    @XmlTransient
    @JsonIgnore
    private Long orderId;

    @XmlElement
    private String productCode;

    @XmlElement
    private Integer quantityOrdered;

    @XmlElement
    private BigDecimal price;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(Long orderId, String productCode,
                           Integer quantityOrdered, BigDecimal price) {
        this.orderId = orderId;
        this.productCode = productCode;
        this.quantityOrdered = quantityOrdered;
        this.price = price;
    }

    public Long getOrderId() {
        return orderId;
    }

    @ApiModelProperty(hidden = true, readOnly = true)
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(Integer quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
