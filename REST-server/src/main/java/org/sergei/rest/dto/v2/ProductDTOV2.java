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
