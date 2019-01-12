/*
 * Copyright 2018-2019 Sergei Visotsky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.rest.repository;

import org.sergei.rest.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.customer.customerId = :customerId")
    List<Order> findAllByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT o FROM Order o WHERE o.customer.customerId = :customerId")
    Page<Order> findAllByCustomerPaginatedId(@Param("customerId") Long customerId, Pageable pageable);

    @Query("SELECT o FROM Order o INNER JOIN OrderDetails od ON o.orderId = od.order.orderId WHERE od.product.productCode = :productCode")
    List<Order> findAllByProductCode(@Param("productCode") String productCode);

    @Transactional
    @Modifying
    @Query("DELETE FROM Order o WHERE o.customer.customerId = ?1 AND o.orderId = ?2")
    void deleteByCustomerIdAndOrderId(Long customerId, Long orderId);
}
