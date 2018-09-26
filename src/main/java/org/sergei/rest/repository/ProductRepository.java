package org.sergei.rest.repository;

import org.sergei.rest.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product WHERE productCode = :productCode")
    Product findByCode(@Param("productCode") String productCode);

    @Query("DELETE FROM Product WHERE productCode = :productCode")
    void deleteProductByProductCode(@Param("productCode") String productCode);

    @Query("SELECT CASE WHEN count(p) > 0 THEN true ELSE false END FROM Product p WHERE p.productCode = :productCode")
    boolean existsByProductCode(@Param("productCode") String productCode);
}
