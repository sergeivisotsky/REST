package org.sergei.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "Customer model", description = "Customer data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    @ApiModelProperty("Customer ID")
    private Long customerId;

    @ApiModelProperty("Customer first name")
    private String firstName;

    @ApiModelProperty("Customer last name")
    private String lastName;

    @ApiModelProperty("Customer age")
    private Integer age;
}
