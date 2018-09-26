package org.sergei.rest.repository;

import org.sergei.rest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT c FROM Order WHERE orderNumber = :orderNumber")
    List<Order> findByNumber(Long orderNumber);

    @Query("SELECT c FROM Order WHERE customerNumber = :customerNumber AND orderNumber = :orderNumber")
    List<Order> findByCustomerAndOrderNumbers(@Param("customerNumber") Long customerNumber,
                                              @Param("orderNumber") Long orderNumber);

    @Query("SELECT c FROM Order WHERE customerNumber = :customerNumber")
    List<Order> findAllByCustomerNumber(@Param("customerNumber") Long customerNumber);

    @Query("SELECT c FROM Order WHERE productCode = :productCode")
    List<Order> findAllByProductCode(@Param("productCode") String productCode);

    @Query("DELETE FROM Order WHERE orderNumber = :orderNumber")
    void deleteByOrderNumber(@Param("orderNumber") Long orderNumber);

    @Query("DELETE FROM Order WHERE customer = :customerNumber AND orderNumber = :orderNumber")
    void deleteByCustomerAndOrderNumbers(@Param("customerNumber") Long customerNumber,
                                         @Param("orderNumber") Long orderNumber);

    @Query("SELECT CASE WHEN count(c) > 0 THEN true ELSE false END FROM Order c WHERE c.orderNumber = :orderNumber")
    boolean existsByNumber(@Param("orderNumber") Long orderNumber);

    @Query("SELECT CASE WHEN count(c) > 0 THEN true ELSE false END FROM Order c WHERE c.customer = :customerNumber")
    boolean existsByCustomerNumber(@Param("customerNumber") Long customerNumber);

    @Query("SELECT CASE WHEN count(c) > 0 THEN true ELSE false END FROM Order c WHERE c.productCode = :productCode")
    boolean existsByProductCode(@Param("productCode") String productCode);
}
