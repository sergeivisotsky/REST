package org.sergei.rest.dto.v2;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    @ApiModelProperty("Date when order should be delivered")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date requiredDate;

    @ApiModelProperty("Date when order was delivered")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date shippedDate;
    private String status;

    @ApiModelProperty("List of order details")
    @JsonProperty("orderDetails")
    private List<OrderDetailsDTO> orderDetailsDTO = new LinkedList<>();
}
