package org.sergei.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "Product", description = "All product meta data")
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

    public ProductDTO() {
    }

    public ProductDTO(String productCode, String productName,
                      String productLine, String productVendor, BigDecimal price) {
        this.productCode = productCode;
        this.productName = productName;
        this.productLine = productLine;
        this.productVendor = productVendor;
        this.price = price;
    }

    public String getProductCode() {
        return productCode;
    }

    @ApiModelProperty(hidden = true)
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getProductVendor() {
        return productVendor;
    }

    public void setProductVendor(String productVendor) {
        this.productVendor = productVendor;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
