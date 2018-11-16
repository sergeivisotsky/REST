package org.sergei.rest.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Sergei Visotsky, 2018
 */
@Entity
@Table(name = "order_details")
public class OrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "details_id")
    @SequenceGenerator(name = "details_id", sequenceName = "details_id", allocationSize = 1)
    @Column(name = "details_id")
    private Long details_id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    // TODO: ON DELETE CASCADE programmatically
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_code")
    private Product product;

    @Column(name = "quantity_ordered", nullable = false)
    private Integer quantityOrdered;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    public OrderDetails() {
    }

    public OrderDetails(Long details_id, Product product, Integer quantityOrdered, BigDecimal price, Order order) {
        this.details_id = details_id;
        this.product = product;
        this.quantityOrdered = quantityOrdered;
        this.price = price;
        this.order = order;
    }

    public Long getDetails_id() {
        return details_id;
    }

    public void setDetails_id(Long details_id) {
        this.details_id = details_id;
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
