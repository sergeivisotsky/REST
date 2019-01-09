/*
 * Copyright 2018-2019 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.sergei.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "Order", description = "All order data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    @ApiModelProperty("Order ID")
    private Long orderId;

    @ApiModelProperty("Customer ID who made an order")
    private Long customerId;

    @ApiModelProperty("Date when order was made")
    private LocalDateTime orderDate;

    @ApiModelProperty("Date when order should be delivered")
    private LocalDateTime requiredDate;

    @ApiModelProperty("Date when order was delivered")
    private LocalDateTime shippedDate;

    @ApiModelProperty("Order status e.g. pending/delivered")
    private String status;

    @ApiModelProperty("List of order details")
    @JsonProperty("orderDetails")
    private List<OrderDetailsDTO> orderDetailsDTO = new LinkedList<>();
}
