package org.sergei.rest.client.pojo;

public class CustomerVO {
    private Long customerNumber;
    private String firstName;
    private String LastName;
    private Integer age;

    public CustomerVO() {
    }

    public CustomerVO(Long customerNumber, String firstName, String lastName, Integer age) {
        this.customerNumber = customerNumber;
        this.firstName = firstName;
        LastName = lastName;
        this.age = age;
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
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "CustomerVO{" +
                "customerNumber=" + customerNumber +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", age=" + age +
                '}';
    }
}
