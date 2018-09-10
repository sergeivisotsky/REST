package org.sergei.rest.service;

import org.sergei.rest.dao.PhotoDAO;
import org.sergei.rest.exceptions.FileNotFoundException;
import org.sergei.rest.exceptions.FileStorageException;
import org.sergei.rest.ftp.FileOperations;
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

@Service
public class PhotoService {

    //    @Value("${file.tmp.path}")
    private static final String TEMP_DIR_PATH = "D:/Program files/servers/apache-tomcat-9.0.10_API/webapps/static/tmp";

    private final PhotoDAO photoDAO;

    private final Path fileStorageLocation;

    private final FileOperations fileOperations;

    @Autowired
    public PhotoService(PhotoDAO photoDAO, FileOperations fileOperations) {
        this.fileStorageLocation = Paths.get(TEMP_DIR_PATH).toAbsolutePath().normalize();
        this.photoDAO = photoDAO;
        this.fileOperations = fileOperations;
    }

    // Method to upload file on the server
    public PhotoUploadResponse uploadFileOnTheServer(Long customerId, String fileDownloadUri,
                                                     CommonsMultipartFile commonsMultipartFile) {
        PhotoUploadResponse photoUploadResponse = new PhotoUploadResponse();
        photoUploadResponse.setCustomerId(customerId);

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
        fileOperations.uploadFile(commonsMultipartFile);

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

        fileOperations.downloadFile(fileName);

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
    public PhotoUploadResponse deletePhoto(Long customerId) throws IOException {
        /*if (!photoDAO.existsByCustomerId(customerId)) {
            throw new ResourceNotFoundException("File not found");
        }*/
        PhotoUploadResponse photoUploadResponse = photoDAO.findPhotoByCustomerId(customerId);
        photoUploadResponse.setCustomerId(customerId);
        Path targetLocation = this.fileStorageLocation.resolve(photoUploadResponse.getFileName());
        Files.deleteIfExists(targetLocation);

        fileOperations.deleteFile(photoUploadResponse.getFileName());

        photoDAO.deleteFileByCustomerId(customerId);

        return photoUploadResponse;
    }
}
