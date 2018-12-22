package org.sergei.rest.client.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sergei Visotsky
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerVO {
    private Long customerId;
    private String firstName;
    private String lastName;
    private Integer age;
}
