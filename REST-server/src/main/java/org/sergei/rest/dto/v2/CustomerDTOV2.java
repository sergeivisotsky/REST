package org.sergei.rest.dto.v2;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

/**
 * V2 of customer DTO
 *
 * @author Sergei Visotsky
 */
@ApiModel(value = "Customer model V2", description = "Customer data")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTOV2 extends ResourceSupport {

    @ApiModelProperty("Customer ID")
    private Long customerId;

    @ApiModelProperty("Customer first name")
    private String firstName;

    @ApiModelProperty("Customer last name")
    private String lastName;

    @ApiModelProperty("Customer age")
    private Integer age;
}
