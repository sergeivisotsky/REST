package org.sergei.rest.dto;

import io.swagger.annotations.ApiModel;

import javax.xml.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(value = "Customer", description = "Customer data")
public class CustomerDTO {

    @XmlElement
    private Long customerId;

    @XmlElement
    private String firstName;

    @XmlElement
    private String lastName;

    @XmlElement
    private Integer age;

    @XmlElementWrapper(name = "orderDTO")
    @XmlElement(name = "order")
    private List<OrderDTO> orderDTOList = new LinkedList<>();

    public CustomerDTO() {
    }

    public CustomerDTO(Long customerId, String firstName, String lastName, Integer age, List<OrderDTO> orderDTOList) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.orderDTOList = orderDTOList;
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

    public List<OrderDTO> getOrderDTOList() {
        return orderDTOList;
    }

    public void setOrderDTOList(List<OrderDTO> orderDTOList) {
        this.orderDTOList = orderDTOList;
    }
}
