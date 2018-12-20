package org.sergei.rest.client.pojo;

/**
 * @author Sergei Visotsky
 */
public class CustomerVO {
    private Long customerId;
    private String firstName;
    private String lastName;
    private Integer age;

    public CustomerVO() {
    }

    public CustomerVO(Long customerId, String firstName, String lastName, Integer age) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
