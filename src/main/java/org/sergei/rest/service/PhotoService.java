package org.sergei.rest.service;

import org.sergei.rest.dao.PhotoDAO;
import org.sergei.rest.exceptions.FileNotFoundException;
import org.sergei.rest.exceptions.FileStorageException;
import org.sergei.rest.model.PhotoUploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class PhotoService {

    private static final String STORAGE_PATH = "D:/Program files/servers/apache-tomcat-9.0.10_API/webapps/static/photo/";

    private final PhotoDAO photoDAO;

    private final Path fileStorageLocation;

    @Autowired
    public PhotoService(PhotoDAO photoDAO) {
        this.fileStorageLocation = Paths.get(STORAGE_PATH).toAbsolutePath().normalize();
        this.photoDAO = photoDAO;
    }

    // Method to upload file on the server
    public PhotoUploadResponse uploadFileOnTheServer(Long customerId, String fileDownloadUri,
                                                     CommonsMultipartFile commonsMultipartFile) throws Exception {

        if (fileDownloadUri.length() > 150) {
            throw new FileStorageException("Too long file name");
        }

        // Filename recreation
        String fileName = StringUtils.cleanPath(commonsMultipartFile.getOriginalFilename());

        // Check if file contains inappropriate symbols
        if (fileName.contains("..")) {
            throw new FileStorageException("Invalid path sequence");
        }

        // Store files on the server directory
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        Files.copy(commonsMultipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        // Save file metadata into a database
        photoDAO.save(customerId, fileDownloadUri, commonsMultipartFile);

        return new PhotoUploadResponse(customerId,
                commonsMultipartFile.getOriginalFilename(),
                fileDownloadUri, commonsMultipartFile.getContentType(),
                commonsMultipartFile.getSize());
    }

    // Method to download file from the server
    public Resource downloadFileAsResource(Long customerId) throws MalformedURLException {
        // Get filename by customer id written in database
        /*if (!photoDAO.existsByCustomerId(customerId)) {
            throw new FileNotFoundException("File not found");
        }
*/
        String fileName = photoDAO.findFileNameByCustomerId(customerId);
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        // Check if file exists or not
        if (resource.exists()) {
            return resource;
        } else {
            throw new FileNotFoundException("File not found");
        }
    }

    // Method to perform file deletion
    public void deletePhoto(Long customerId) throws IOException {
        /*if (!photoDAO.existsByCustomerId(customerId)) {
            throw new ResourceNotFoundException("File not found");
        }*/
        String fileName = photoDAO.findFileNameByCustomerId(customerId);
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        Files.deleteIfExists(targetLocation);

        photoDAO.deleteFileByCustomerId(customerId);
    }
}
