package org.sergei.rest.repository;

import org.sergei.rest.model.PhotoUploadResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoUploadResponse, Long> {
    // TODO: existsByPhotoId
    // TODO: existsByCustomerNumber
    // TODO: existsByPhotoName
    // TODO:
}
