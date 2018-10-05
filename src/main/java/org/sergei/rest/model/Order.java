package org.sergei.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "order_number")
    private Long orderNumber;

    @Column(name = "order_date")
    @CreationTimestamp
    private Date orderDate;

    @Column(name = "required_date")
    @CreationTimestamp
    private Date requiredDate;

    @Column(name = "shipped_date")
    @CreationTimestamp
    private Date shippedDate;

    @Column(name = "status")
    private String status;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "order_number")
    private List<OrderDetails> orderDetails = new LinkedList<>();

    @ManyToOne
    @JoinColumn(name = "customer_number")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;

    public Order() {
    }

    public Order(Long orderNumber, Customer customer, Date orderDate,
                 Date requiredDate, Date shippedDate, String status) {
        this.orderNumber = orderNumber;
        this.customer = customer;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.status = status;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    @JsonIgnore
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(getOrderNumber(), order.getOrderNumber()) &&
                Objects.equals(getOrderDate(), order.getOrderDate()) &&
                Objects.equals(getRequiredDate(), order.getRequiredDate()) &&
                Objects.equals(getShippedDate(), order.getShippedDate()) &&
                Objects.equals(getStatus(), order.getStatus()) &&
                Objects.equals(getOrderDetails(), order.getOrderDetails()) &&
                Objects.equals(getCustomer(), order.getCustomer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderNumber(), getOrderDate(), getRequiredDate(),
                getShippedDate(), getStatus(), getOrderDetails(), getCustomer());
    }
}
