package org.sergei.rest.service;

import org.sergei.rest.dao.PhotoDAO;
import org.sergei.rest.exceptions.ResourceNotFoundException;
import org.sergei.rest.exceptions.TooLongFileNameException;
import org.sergei.rest.ftp.FileOperations;
import org.sergei.rest.model.PhotoUploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PhotoService {

    @Autowired
    private FileOperations fileOperations;

    @Autowired
    private PhotoDAO photoDAO;

    public PhotoUploadResponse uploadFileOnTheServer(Long customerId, String fileDownloadUri,
                                                     MultipartFile multipartFile) {

        if (fileDownloadUri.length() > 150) {
            throw new TooLongFileNameException("Too long file name");
        }

        fileOperations.uploadFile(multipartFile);

        photoDAO.save(customerId, fileDownloadUri, multipartFile);

        return new PhotoUploadResponse(customerId,
                multipartFile.getOriginalFilename(),
                fileDownloadUri, multipartFile.getContentType(),
                multipartFile.getSize());
    }

    public Resource downloadFileAsResource(Long customerId) throws MalformedURLException {
        if (!photoDAO.existsByCustomerId(customerId)) {
            throw new ResourceNotFoundException("File not found");
        }
        String fileName = photoDAO.findFileNameByCustomerId(customerId);
        fileOperations.downloadFile(fileName);
        Path filePath = Paths
                .get("D:/Program files/servers/apache-tomcat-9.0.10_API/webapps/static/tmp/" + fileName)
                .toAbsolutePath().normalize();

        return new UrlResource(filePath.toUri());
    }

    public void deletePhoto(Long customerId) {
        if (!photoDAO.existsByCustomerId(customerId)) {
            throw new ResourceNotFoundException("Photo not found");
        }
        String fileName = photoDAO.findFileNameByCustomerId(customerId);
        fileOperations.deleteFile(fileName);
        photoDAO.deleteFileByCustomerId(customerId);
    }
}
