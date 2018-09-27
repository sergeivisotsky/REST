package org.sergei.rest.repository;

import org.sergei.rest.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM products WHERE product_code = :productCode", nativeQuery = true)
    Product findByCode(@Param("productCode") String productCode);

    @Query(value = "DELETE FROM products WHERE product_code = :productCode", nativeQuery = true)
    void deleteProductByProductCode(@Param("productCode") String productCode);
}
