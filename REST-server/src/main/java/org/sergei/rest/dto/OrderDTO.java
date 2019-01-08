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
