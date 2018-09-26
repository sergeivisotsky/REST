package org.sergei.rest.repository;

import org.sergei.rest.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT CASE WHEN count(c) > 0 THEN true ELSE false END FROM Customer c WHERE c.customerNumber = :customerNumber")
    boolean existsByCustomerNumber(@Param("customerNumber") Long customerNumber);

    @Query("SELECT c FROM Customer c WHERE c.customerNumber = :customerNumber")
    Customer findByCustomerNumber(@Param("customerNumber") Long customerNumber);
}
