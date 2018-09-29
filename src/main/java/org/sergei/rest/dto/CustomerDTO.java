package org.sergei.rest.dto;

import java.util.LinkedList;
import java.util.List;

public class CustomerDTO {
    private Long customerNumber;

    private String firstName;

    private String lastName;

    private Integer age;

    private List<OrderDTO> orders = new LinkedList<>();

    public CustomerDTO() {
    }

    public CustomerDTO(Long customerNumber, String firstName, String lastName, Integer age, List<OrderDTO> orders) {
        this.customerNumber = customerNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.orders = orders;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }
}
