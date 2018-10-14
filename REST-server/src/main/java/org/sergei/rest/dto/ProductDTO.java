package org.sergei.rest.dto;

import java.math.BigDecimal;

public class ProductDTO {

    private String productCode;
    private String productName;
    private String productLine;
    private String productVendor;
    private BigDecimal price;

    public ProductDTO() {
    }

    // TODO: Convertion from model to DTO and vise versa in constructor
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
