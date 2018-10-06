package org.sergei.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModel;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@JsonRootName("orderDetails")
@XmlRootElement(name = "orderDetails")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(value = "Order details", description = "All oder details data")
public class OrderDetailsDTO {

    @XmlElement
    @XmlTransient
    @JsonIgnore
    private Long orderNumber;

    @XmlElement
    private String productCode;

    @XmlElement
    private Integer quantityOrdered;

    @XmlElement
    private BigDecimal price;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(Long orderNumber, String productCode,
                           Integer quantityOrdered, BigDecimal price) {
        this.orderNumber = orderNumber;
        this.productCode = productCode;
        this.quantityOrdered = quantityOrdered;
        this.price = price;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
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
