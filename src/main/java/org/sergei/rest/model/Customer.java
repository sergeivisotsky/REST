package org.sergei.rest.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "customer_number")
    private Long customerNumber;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "customer_number")
    private List<Order> orders = new LinkedList<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "customer_number")
    private List<Photo> photoUploadResponse = new LinkedList<>();

    public Customer() {
    }

    public Customer(String firstName, String lastName, Integer age,
                    List<Order> orders, List<Photo> photoUploadResponse) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.orders = orders;
        this.photoUploadResponse = photoUploadResponse;
    }

    public Long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Photo> getPhotoUploadResponse() {
        return photoUploadResponse;
    }

    public void setPhotoUploadResponse(List<Photo> photoUploadResponse) {
        this.photoUploadResponse = photoUploadResponse;
    }
}
