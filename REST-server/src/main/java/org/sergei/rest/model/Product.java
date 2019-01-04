package org.sergei.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Sergei Visotsky
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
