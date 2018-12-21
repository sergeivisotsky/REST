package org.sergei.rest.repository;

import org.sergei.rest.model.CustomerReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface CustomerReportRepository extends JpaRepository<CustomerReport, Long> {
    List<CustomerReport> findByCustomerId(Long customerId);

    Page<CustomerReport> findPaginatedByCustomerId(Long customerId, Pageable pageable);
}
