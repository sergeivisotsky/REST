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

package org.sergei.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "Product", description = "All product meta data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

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
