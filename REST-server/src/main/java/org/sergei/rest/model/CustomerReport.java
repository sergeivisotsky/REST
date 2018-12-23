package org.sergei.rest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Sergei Visotsky
 */
@ApiModel(
        value = "Customer report model",
        description = "Customer report data"
)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Immutable
@Table(name = "customer_report_view")
public class CustomerReport extends ResourceSupport implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("Customer ID")
    @Column(name = "customer_id")
    private Long customerId;

    @ApiModelProperty("Customer first name")
    @Column(name = "first_name")
    private String firstName;

    @ApiModelProperty("Customer last name")
    @Column(name = "last_name")
    private String lastName;

    @ApiModelProperty("Order ID")
    @Id
    @Column(name = "order_id")
    private Long orderId;

    @ApiModelProperty("Date when order was made")
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @ApiModelProperty("Date when order should be delivered")
    @Column(name = "required_date")
    private LocalDateTime requiredDate;

    @ApiModelProperty("Date when order was delivered")
    @Column(name = "shipped_date")
    private LocalDateTime shippedDate;
}
