package org.sergei.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

/**
 * @author Sergei Visotsky, 2018
 */
@ApiModel(value = "Product", description = "All product meta data")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductDTO {

    @XmlElement
    private String productCode;

    @XmlElement
    private String productName;

    @XmlElement
    private String productLine;

    @XmlElement
    private String productVendor;

    @XmlElement
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

    @ApiModelProperty(hidden = true, readOnly = true)
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
