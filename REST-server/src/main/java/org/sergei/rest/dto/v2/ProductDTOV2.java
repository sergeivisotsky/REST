/*
 * Copyright 2018-2019 Sergei Visotsky
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

import java.math.BigDecimal;

/**
 * V2 of product DTO
 *
 * @author Sergei Visotsky
 */
@ApiModel(value = "Product V2", description = "All product meta data")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTOV2 extends ResourceSupport {

    @ApiModelProperty("Product code")
    private String productCode;

    @ApiModelProperty("Product name")
    private String productName;

    @ApiModelProperty("Product line e.g. cars")
    private String productLine;

    @ApiModelProperty("Product vendor e.g. Volkswagen")
    private String productVendor;

    @ApiModelProperty("Car price")
    private BigDecimal price;
}
