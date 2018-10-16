package org.sergei.rest.dto;

import org.sergei.rest.model.Customer;

public class CustomerDTO {
    private Long customerId;
    private String firstName;
    private String lastName;
    private int age;

    public CustomerDTO() {
    }

    public CustomerDTO(Customer customer) {
        this.customerId = customer.getCustomerNumber();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.age = customer.getAge();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public void setAge(int age) {
        this.age = age;
    }

    public Customer toModelObject() {
        Customer customer = new Customer();

        customer.setCustomerNumber(customerId);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAge(age);

        return customer;
    }
}
