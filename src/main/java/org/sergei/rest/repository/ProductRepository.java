package org.sergei.rest.repository;

import org.sergei.rest.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // TODO: existsByProductCode
    // TODO: All needed
    // TODO: existsByProductCode
}
