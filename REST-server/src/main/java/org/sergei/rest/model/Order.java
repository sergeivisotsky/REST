package org.sergei.rest.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Data
@NoArgsConstructor
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
    private LocalDateTime orderDate;

    @Column(name = "required_date", nullable = false)
    private LocalDateTime requiredDate;

    @Column(name = "shipped_date", nullable = false)
    private LocalDateTime shippedDate;

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

    public Order(Customer customer, LocalDateTime orderDate,
                 LocalDateTime requiredDate, LocalDateTime shippedDate, String status) {
        this.customer = customer;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.status = status;
    }
}
