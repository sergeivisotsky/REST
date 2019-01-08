package org.sergei.rest.dto.v2;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.sergei.rest.dto.OrderDetailsDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * V2 of order DTO
 *
 * @author Sergei Visotsky
 */
@ApiModel(value = "Order V2", description = "All order data")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTOV2 extends ResourceSupport {

    @ApiModelProperty("Order ID")
    private Long orderId;

    @ApiModelProperty("Customer ID who made an order")
    private Long customerId;

    @ApiModelProperty("Date when order was made")
    private Date orderDate;

    @ApiModelProperty("Date when order should be delivered")
    private Date requiredDate;

    @ApiModelProperty("Date when order was delivered")
    private Date shippedDate;

    @ApiModelProperty("Order status e.g. pending/delivered")
    private String status;

    @ApiModelProperty("List of order details")
    @JsonProperty("orderDetails")
    private List<OrderDetailsDTO> orderDetailsDTO = new LinkedList<>();
}
