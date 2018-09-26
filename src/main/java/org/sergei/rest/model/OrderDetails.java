package org.sergei.rest.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "order_details")
public class OrderDetails implements Serializable {

    @XmlElement
    @Id
    @ManyToOne
    @JoinColumn(name = "product_code")
    private Product productCode;

    @XmlElement
    @Column(name = "quantity_ordered")
    private Integer quantityOrdered;

    @XmlElement
    @Column(name = "price")
    private BigDecimal price;

    public OrderDetails() {
    }

    public OrderDetails(Product productCode, Integer quantityOrdered, BigDecimal price) {
        this.productCode = productCode;
        this.quantityOrdered = quantityOrdered;
        this.price = price;
    }

    public Product getProductCode() {
        return productCode;
    }

    public void setProductCode(Product productCode) {
        this.productCode = productCode;
    }

    public int getQuantityOrdered() {
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
