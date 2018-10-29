/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "product_code", length = 15)
    private String productCode;

    @Column(name = "product_name", length = 70, nullable = false)
    private String productName;

    @Column(name = "product_line", length = 50, nullable = false)
    private String productLine;

    @Column(name = "product_vendor", length = 50, nullable = false)
    private String productVendor;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
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
