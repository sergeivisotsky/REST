/*
 * Copyright 2018-2019 the original author.
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

import org.sergei.rest.model.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query("SELECT p FROM Photo p WHERE p.customer.customerId = :customerId")
    List<Photo> findAllPhotosByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT p FROM Photo p WHERE p.customer.customerId = :customerId")
    Page<Photo> findAllPhotosByCustomerIdPaginated(@Param("customerId") Long customerId, Pageable pageable);

    @Query("SELECT p FROM Photo p WHERE p.customer.customerId = :customerId and p.fileName = :fileName")
    Optional<Photo> findPhotoByCustomerIdAndFileName(@Param("customerId") Long customerId,
                                                     @Param("fileName") String fileName);

    @Query("SELECT p FROM Photo p WHERE p.customer.customerId = :customerId AND p.photoId = :photoId")
    Optional<Photo> findByCustomerIdAndPhotoId(@Param("customerId") Long customerId,
                                               @Param("photoId") Long photoId);

    @Query("SELECT p.fileUrl FROM Photo p WHERE p.customer.customerId = :customerId")
    List<String> findFileUrlByCustomerId(Long customerId);
}
