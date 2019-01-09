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

package org.sergei.rest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@ApiModel(
        value = "Customer report model",
        description = "Customer report data"
)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Immutable
@Table(name = "customer_report_view")
public class CustomerReport extends ResourceSupport implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("Customer ID")
    @Column(name = "customer_id")
    private Long customerId;

    @ApiModelProperty("Customer first name")
    @Column(name = "first_name")
    private String firstName;

    @ApiModelProperty("Customer last name")
    @Column(name = "last_name")
    private String lastName;

    @ApiModelProperty("Order ID")
    @Id
    @Column(name = "order_id")
    private Long orderId;

    @ApiModelProperty("Date when order was made")
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @ApiModelProperty("Date when order should be delivered")
    @Column(name = "required_date")
    private LocalDateTime requiredDate;

    @ApiModelProperty("Date when order was delivered")
    @Column(name = "shipped_date")
    private LocalDateTime shippedDate;
}
