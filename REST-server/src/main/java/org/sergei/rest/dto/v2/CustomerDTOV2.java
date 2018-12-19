package org.sergei.rest.dto.v2;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.ResourceSupport;

/**
 * V2 of customer DTO
 * @author Sergei Visotsky
 * @since 2.0.2
 * Created on 12/9/2018
 */
@ApiModel(value = "Customer model V2", description = "Customer data")
public class CustomerDTOV2 extends ResourceSupport {

    private Long customerId;
    private String firstName;
    private String lastName;
    private Integer age;

    public CustomerDTOV2() {
    }

    public CustomerDTOV2(Long customerId, String firstName, String lastName, Integer age) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getCustomerId() {
        return customerId;
    }

    @ApiModelProperty(hidden = true)
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
