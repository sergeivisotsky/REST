package org.sergei.rest.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_seq", allocationSize = 1)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @Column(name = "required_date", nullable = false)
    private Date requiredDate;

    @Column(name = "shipped_date", nullable = false)
    private Date shippedDate;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "order_id")
    private List<OrderDetails> orderDetails = new LinkedList<>();

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Order() {
    }

    public Order(Customer customer, Date orderDate,
                 Date requiredDate, Date shippedDate, String status) {
        this.customer = customer;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
