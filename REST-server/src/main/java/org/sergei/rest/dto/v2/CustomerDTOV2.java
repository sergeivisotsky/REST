/*
 * Copyright 2018-2019 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
