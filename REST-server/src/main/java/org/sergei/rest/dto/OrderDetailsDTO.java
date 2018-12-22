package org.sergei.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "OrderDetails", description = "All oder details data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("orderDetails")
public class OrderDetailsDTO {

    @JsonIgnore
    private Long orderId;

    @ApiModelProperty("Product code which was orders")
    private String productCode;

    @ApiModelProperty("Quantity of the product ordered")
    private Integer quantityOrdered;

    @ApiModelProperty("Order price")
    private BigDecimal price;
}
