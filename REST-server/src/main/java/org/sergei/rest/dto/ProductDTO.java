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
