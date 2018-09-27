package org.sergei.rest.repository;

import org.sergei.rest.model.Customer;
import org.sergei.rest.model.PhotoUploadResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoUploadResponse, Long> {
    @Query(value = "SELECT * FROM photos WHERE customer_number = :customerNumber", nativeQuery = true)
    Optional<List<PhotoUploadResponse>> findAllPhotosByCustomerNumber(@Param("customerNumber") Long customerNumber);

    @Query(value = "SELECT * FROM photos WHERE customer_number = :customerNumber AND file_name = :fileName", nativeQuery = true)
    Optional<PhotoUploadResponse> findPhotoByCustomerNumberAndFileName(@Param("customerNumber") Long customerNumber,
                                                             @Param("fileName") String fileName);

    @Query(value = "SELECT * FROM photos WHERE customer_number = :customerNumber AND photo_id = :photoId", nativeQuery = true)
    Optional<PhotoUploadResponse> findPhotoMetaByCustomerNumberAndFileId(@Param("customerNumber") Long customerNumber,
                                                               @Param("photoId") Long photoId);

    @Query(value = "DELETE FROM photos WHERE customer_number = :customerNumber AND photo_id = :photoId", nativeQuery = true)
    void deleteFileByCustomerNumberAndFileId(@Param("customerNumber") Long customerNumber,
                                             @Param("photoId") Long photoId);
}
