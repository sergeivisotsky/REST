package org.sergei.rest.repository;

import org.sergei.rest.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
