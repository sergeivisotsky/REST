package org.sergei.rest.service.v2;

import org.sergei.rest.dto.v2.CustomerDTOV2;
import org.sergei.rest.exceptions.ResourceNotFoundException;
import org.sergei.rest.model.Customer;
import org.sergei.rest.repository.CustomerRepository;
import org.sergei.rest.service.CustomerService;
import org.sergei.rest.util.ObjectMapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergei Visotsky
 * @since 12/9/2018
 * <p>
 * V2 of customer service
 */
@Service
public class CustomerServiceV2 extends CustomerService {

    public CustomerServiceV2(CustomerRepository customerRepository) {
        super(customerRepository);
    }

    /**
     * Get all customers
     *
     * @return List of customer DTOs list
     */
    public List<CustomerDTOV2> findAllV2() {
        List<CustomerDTOV2> customerDTOList = new LinkedList<>();

        List<Customer> customers = customerRepository.findAll();

        customers.forEach(customer -> {
            CustomerDTOV2 customerDTOV2 = new CustomerDTOV2();
            customerDTOV2.setCustomerId(customer.getCustomerId());
            customerDTOV2.setFirstName(customer.getFirstName());
            customerDTOV2.setLastName(customer.getLastName());
            customerDTOV2.setAge(customer.getAge());

            customerDTOList.add(customerDTOV2);
        });

        return customerDTOList;
    }

    /**
     * Get all customers paginated
     *
     * @return List of customer DTOs list
     */
    public Page<CustomerDTOV2> findAllPaginatedV2(int page, int size) {
        Page<Customer> customers = customerRepository.findAll(PageRequest.of(page, size));
        return ObjectMapperUtil.mapAllPages(customers, CustomerDTOV2.class);
    }

    /**
     * Get customer by id
     *
     * @param customerId get customer number param from REST controller
     * @return Customer DTO response
     */
    public CustomerDTOV2 findOneV2(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );
        return ObjectMapperUtil.map(customer, CustomerDTOV2.class);
    }
}
