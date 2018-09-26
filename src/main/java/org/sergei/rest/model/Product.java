package org.sergei.rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "product")
public class Product implements Serializable {

    @XmlElement
    @Id
    @Column(name = "product_code", length = 15)
    private String productCode;

    @XmlElement
    @Column(name = "product_name", length = 70)
    private String productName;

    @XmlElement
    @Column(name = "product_line", length = 50)
    private String productLine;

    @XmlElement
    @Column(name = "product_vendor", length = 50)
    private String productVendor;

    @XmlElement
    @Column(name = "price", precision=10, scale=2)
    private BigDecimal price;

    public Product() {
    }

    public Product(String productName, String productLine, String productVendor, BigDecimal price) {
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
