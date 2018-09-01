package org.sergei.rest.service;

import org.sergei.rest.dao.PhotoUploadDAO;
import org.sergei.rest.exceptions.ResourceNotFoundException;
import org.sergei.rest.exceptions.TooLongFileNameException;
import org.sergei.rest.ftp.FileUploader;
import org.sergei.rest.model.PhotoUploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class PhotoUploadService {

    @Autowired
    private FileUploader fileUploader;

    @Autowired
    private PhotoUploadDAO photoUploadDAO;

    public PhotoUploadResponse uploadFileOnTheServer(Long customerId, String fileDownloadUri,
                                                     CommonsMultipartFile commonsMultipartFile) {

        if (fileDownloadUri.length() > 150) {
            throw new TooLongFileNameException("Too long file name");
        }

        fileUploader.uploadFile(commonsMultipartFile);

        photoUploadDAO.save(customerId, fileDownloadUri, commonsMultipartFile);

        return new PhotoUploadResponse(customerId,
                commonsMultipartFile.getOriginalFilename(),
                fileDownloadUri, commonsMultipartFile.getContentType(),
                commonsMultipartFile.getSize());
    }

    public Resource downloadFileAsResource(Long customerId) throws MalformedURLException {
        String fileName = photoUploadDAO.findFileNameByCustomerId(customerId);
        fileUploader.downloadFile(fileName);
        Path filePath = Paths
                .get("D:/Users/Sergei/Documents/JavaProjects/REST/src/main/resources/tmp/" + fileName)
                .toAbsolutePath().normalize();

        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new ResourceNotFoundException("File not found");
        }

        return resource;
    }
}
