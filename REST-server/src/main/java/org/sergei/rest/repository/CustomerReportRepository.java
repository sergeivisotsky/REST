package org.sergei.rest.repository;

import org.sergei.rest.model.CustomerReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface CustomerReportRepository extends JpaRepository<CustomerReport, Long> {
    List<CustomerReport> findByCustomerId(Long customerId);
}
