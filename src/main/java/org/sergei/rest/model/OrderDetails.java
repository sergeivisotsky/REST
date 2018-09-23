package org.sergei.rest.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderDetails {

    @XmlElement
    private String productCode;

    @XmlElement
    private int quantityOrdered;

    @XmlElement
    private BigDecimal price;

    public OrderDetails() {
    }

    public OrderDetails(String productCode, int quantityOrdered, BigDecimal price) {
        this.productCode = productCode;
        this.quantityOrdered = quantityOrdered;
        this.price = price;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
