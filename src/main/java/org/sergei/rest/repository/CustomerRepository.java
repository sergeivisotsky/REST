package org.sergei.rest.repository;

import org.sergei.rest.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

//    @Query(value = "SELECT CASE WHEN count(*) > 0 THEN true ELSE false END FROM customers WHERE customer_number = :customerNumber", nativeQuery = true)
    boolean existsByCustomerNumber(@Param("customerNumber") Long customerNumber);

    @Query(value = "SELECT * FROM customers c WHERE customer_number = :customerNumber", nativeQuery = true)
    Customer findByCustomerNumber(@Param("customerNumber") Long customerNumber);
}
