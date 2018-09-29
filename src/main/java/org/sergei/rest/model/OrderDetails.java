package org.sergei.rest.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement
@Entity
@Table(name = "order_details")
public class OrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    @Id
    @ManyToOne
    @JoinColumn(name = "order_number")
    private Order order;

    @XmlElement
    @Id
    @ManyToOne
    @JoinColumn(name = "product_code")
    private Product product;

    /*@ManyToOne
    @JoinColumn(name = "customer_number")
    private Customer customer;*/

    @XmlElement
    @Column(name = "quantity_ordered")
    private Integer quantityOrdered;

    @XmlElement
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    public OrderDetails() {
    }

    public OrderDetails(Product product, Integer quantityOrdered, BigDecimal price, Order order, Customer customer) {
        this.product = product;
        this.quantityOrdered = quantityOrdered;
        this.price = price;
        this.order = order;
//        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(Integer quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    /*public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }*/

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
