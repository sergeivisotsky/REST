package org.sergei.rest.dto;

import java.math.BigDecimal;

public class OrderDetailsDTO {
    private Long orderNumber;

    private String productCode;

    private Integer quantityOrdered;

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
