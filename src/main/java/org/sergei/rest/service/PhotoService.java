package org.sergei.rest.service;

import org.sergei.rest.dao.PhotoDAO;
import org.sergei.rest.exceptions.FileNotFoundException;
import org.sergei.rest.exceptions.FileStorageException;
import org.sergei.rest.ftp.FileOperations;
import org.sergei.rest.model.Customer;
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
import java.util.List;

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
    public PhotoUploadResponse uploadFileOnTheServer(Customer customerId, String fileDownloadUri,
                                                     CommonsMultipartFile commonsMultipartFile) {
        PhotoUploadResponse photoUploadResponse = new PhotoUploadResponse();
        photoUploadResponse.setCustomer(customerId);

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

    // Method to find all photos by customer number
    public List<PhotoUploadResponse> findAllUploadedPhotos(Customer customerNumber) {
        if (!photoDAO.existsByCustomerNumber(customerNumber)) {
            throw new FileNotFoundException("Customer with this number not found");
        }
        return photoDAO.findAllPhotosByCustomerNumber(customerNumber);
    }

    // Method to download file from the server by file name
    public Resource downloadFileAsResourceByName(Long customerNumber, String fileName) throws MalformedURLException {
        // Get filename by customer id written in database
        /*if (!photoDAO.existsByCustomerNumber(customerNumber)) {
            throw new FileNotFoundException("Customer with this ID not found");
        } else if (!photoDAO.existsByPhotoName(fileName)) {
            throw new FileNotFoundException("Photo with this name not found");
        }*/

        String fileNameResp = photoDAO.findPhotoByCustomerNumberAndFileName(customerNumber, fileName);

        fileOperations.downloadFile(fileNameResp);

        Path filePath = this.fileStorageLocation.resolve(fileNameResp).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        // Check if file exists
        if (resource.exists()) {
            return resource;
        } else {
            throw new FileNotFoundException("File not found");
        }
    }

    // Method to download file from the server by file ID
    public Resource downloadFileAsResourceByFileId(Customer customerId, Long photoId) throws MalformedURLException {
        // Get filename by customer id written in database
        /*if (!photoDAO.existsByCustomerNumber(customerId)) {
            throw new FileNotFoundException("Customer with this ID not found");
        } else if (!photoDAO.existsByPhotoId(photoId)) {
            throw new FileNotFoundException("Photo with this ID not found");
        }*/

        String fileNameResp = photoDAO.findPhotoMetaByCustomerNumberAndFileId(customerId, photoId).getFileName();

        fileOperations.downloadFile(fileNameResp);

        Path filePath = this.fileStorageLocation.resolve(fileNameResp).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        // Check if file exists
        if (resource.exists()) {
            return resource;
        } else {
            throw new FileNotFoundException("File not found");
        }
    }

    // Method to perform file deletion by customer number and photo ID
    public PhotoUploadResponse deletePhoto(Customer customerNumber, Long photoId) throws IOException {
        if (!photoDAO.existsByCustomerNumber(customerNumber)) {
            throw new FileNotFoundException("Customer with this number not found");
        } else if (!photoDAO.existsByPhotoId(photoId)) {
            throw new FileNotFoundException("Photo with this ID not found");
        }
        PhotoUploadResponse photoUploadResponse =
                photoDAO.findPhotoMetaByCustomerNumberAndFileId(customerNumber, photoId);
        photoUploadResponse.setCustomer(customerNumber);

        // Delete photo from temp storage
        Path targetLocation = this.fileStorageLocation.resolve(photoUploadResponse.getFileName());
        Files.deleteIfExists(targetLocation);

        fileOperations.deleteFile(photoUploadResponse.getFileName()); // Delete file from the FTP server
        photoDAO.deleteFileByCustomerNumberAndFileId(customerNumber, photoId); // Delete file metadata from the database

        return photoUploadResponse;
    }
}
