package org.sergei.rest.repository;

import org.sergei.rest.model.Customer;
import org.sergei.rest.model.PhotoUploadResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoUploadResponse, Long> {
    // TODO: existsByPhotoId
    // TODO: existsByCustomerNumber

    @Query("SELECT CASE WHEN count(c) > 0 THEN true ELSE false END FROM PhotoUploadResponse c WHERE c.customer = :customerNumber")
    boolean existsByCustomerNumber(Long customerNumber);

    @Query("SELECT c FROM PhotoUploadResponse WHERE customer = :customerNumber")
    List<PhotoUploadResponse> findAllPhotosByCustomerNumber(@Param("customerNumber") Long customerNumber);

    @Query("SELECT c FROM PhotoUploadResponse WHERE customer = :customerNumber AND fileName = :fileName")
    PhotoUploadResponse findPhotoByCustomerNumberAndFileName(@Param("customerNumber") Long customerNumber,
                                                             @Param("fileName") String fileName);

    @Query("SELECT c FROM PhotoUploadResponse WHERE customer = :customerNumber AND photoId = :photoId")
    PhotoUploadResponse findPhotoMetaByCustomerNumberAndFileId(@Param("customerNumber") Long customerNumber,
                                                               @Param("photoId") Long photoId);

    @Query("SELECT CASE WHEN count(c) > 0 THEN true ELSE false END FROM PhotoUploadResponse c WHERE c.photoId = :photoId")
    boolean existsByPhotoId(@Param("photoId") Long photoId);

    @Query("DELETE FROM PhotoUploadResponse WHERE customer = :customerNumber AND photoId = :photoId")
    void deleteFileByCustomerNumberAndFileId(@Param("customerNumber") Long customerNumber,
                                             @Param("photoId") Long photoId);
}
