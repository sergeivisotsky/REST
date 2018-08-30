package org.sergei.rest.service;

import org.sergei.rest.dao.PhotoUploadDAO;
import org.sergei.rest.ftp.FileUploader;
import org.sergei.rest.model.PhotoUploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Repository
public class PhotoUploadService {

    @Autowired
    private FileUploader fileUploader;

    @Autowired
    private PhotoUploadDAO photoUploadDAO;

    public PhotoUploadResponse uploadFileOnTheServer(Long customerId, String fileDownloadUri,
                                                     CommonsMultipartFile commonsMultipartFile) {
        fileUploader.uploadFile(commonsMultipartFile);

        photoUploadDAO.save(customerId, fileDownloadUri, commonsMultipartFile);

        return new PhotoUploadResponse(customerId,
                commonsMultipartFile.getOriginalFilename(),
                fileDownloadUri, commonsMultipartFile.getContentType(),
                commonsMultipartFile.getSize());
    }
}
