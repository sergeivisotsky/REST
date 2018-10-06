package org.sergei.rest.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
@IdClass(OrderDetailsId.class)
public class OrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "order_number")
    // TODO: ON DELETE CASCADE programmatically
    private Order order;

    @Id
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_code")
    private Product product;

    @Column(name = "quantity_ordered")
    private Integer quantityOrdered;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    public OrderDetails() {
    }

    public OrderDetails(Product product, Integer quantityOrdered, BigDecimal price, Order order) {
        this.product = product;
        this.quantityOrdered = quantityOrdered;
        this.price = price;
        this.order = order;
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
