package org.sergei.rest.service;

import org.modelmapper.ModelMapper;
import org.sergei.rest.dto.PhotoDTO;
import org.sergei.rest.exceptions.FileNotFoundException;
import org.sergei.rest.exceptions.FileStorageException;
import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.ftp.FileOperations;
import org.sergei.rest.model.Photo;
import org.sergei.rest.repository.CustomerRepository;
import org.sergei.rest.repository.PhotoRepository;
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
import java.util.LinkedList;
import java.util.List;

@Service
public class PhotoService {

    //    @Value("${file.tmp.path}")
    private static final String TEMP_DIR_PATH = "D:/Program files/servers/apache-tomcat-9.0.10_API/webapps/static/tmp";

    private final ModelMapper modelMapper;

    private final PhotoRepository photoRepository;

    private final CustomerRepository customerRepository;

    private final Path fileStorageLocation;

    private final FileOperations fileOperations;

    @Autowired
    public PhotoService(ModelMapper modelMapper, PhotoRepository photoRepository,
                        CustomerRepository customerRepository, FileOperations fileOperations) {
        this.modelMapper = modelMapper;
        this.fileStorageLocation = Paths.get(TEMP_DIR_PATH).toAbsolutePath().normalize();
        this.photoRepository = photoRepository;
        this.customerRepository = customerRepository;
        this.fileOperations = fileOperations;
    }

    /**
     * Method to find all photos by customer number
     *
     * @param customerNumber get customer number from the REST controller
     * @return list of the photo DTOs as a response
     */
    public List<PhotoDTO> findAllUploadedPhotos(Long customerNumber) {
        List<PhotoDTO> photoDTOSResponse = new LinkedList<>();

        List<Photo> photos = photoRepository.findAllPhotosByCustomerNumber(customerNumber)
                .orElseThrow(() -> new RecordNotFoundException("No photos for this customer found"));

        for (Photo photo : photos) {
            // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
            PhotoDTO photoDTO = modelMapper.map(photo, PhotoDTO.class);
            photoDTOSResponse.add(photoDTO);
        }

        return photoDTOSResponse;
    }

    /**
     * Method to upload file on the server
     *
     * @param customerNumber       get customer number from the REST controller
     * @param fileDownloadUri      get file download uri created in REST controller
     * @param commonsMultipartFile get file uploaded from the REST controller
     * @return photo DTO response
     */
    public PhotoDTO uploadFileOnTheServer(Long customerNumber, String fileDownloadUri,
                                          CommonsMultipartFile commonsMultipartFile) {

        customerRepository.findById(customerNumber)
                .orElseThrow(() -> new RecordNotFoundException("Customer with this number not found"));

        PhotoDTO photoDTOResponse = new PhotoDTO();

        // FIXME: set photo ID properly due to it is null right now
//        photoDTOResponse.setPhotoId();
        photoDTOResponse.setCustomerNumber(customerNumber);
        photoDTOResponse.setFileName(commonsMultipartFile.getName());
        photoDTOResponse.setFileUrl(fileDownloadUri);
        photoDTOResponse.setFileType(commonsMultipartFile.getContentType());
        photoDTOResponse.setFileSize(commonsMultipartFile.getSize());

        // ModelMapper is used to avoid manual conversion from DTO to entity using setters and getters
        Photo photo = modelMapper.map(photoDTOResponse, Photo.class);

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
        photoRepository.save(photo);

        return photoDTOResponse;
    }

    /**
     * Method to download file from the server by file name
     *
     * @param customerNumber get customer number from the REST controller
     * @param fileName       get file name from the RESt controller
     * @return Resource
     * @throws MalformedURLException throws in case of invalid uri
     */
    public Resource downloadFileAsResourceByName(Long customerNumber, String fileName) throws MalformedURLException {
        // Get filename by customer id written in database
        Photo photo = photoRepository.findPhotoByCustomerNumberAndFileName(customerNumber, fileName)
                .orElseThrow(() -> new RecordNotFoundException("No photo with this parameters found"));

        String responseFileName = photo.getFileName();

        fileOperations.downloadFile(responseFileName);

        Path filePath = this.fileStorageLocation.resolve(responseFileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        // Check if file exists
        if (resource.exists()) {
            return resource;
        } else {
            throw new FileNotFoundException("File not found");
        }
    }

    /**
     * Method to download file from the server by file ID
     *
     * @param customerNumber get customer number from the REST controller
     * @param photoId        get photo IR from the REST controller
     * @return Returns resource
     * @throws MalformedURLException throws in case of invalid uri
     */
    public Resource downloadFileAsResourceByFileId(Long customerNumber, Long photoId) throws MalformedURLException {
        // Get filename by customer id written in database
        Photo photo = photoRepository.findPhotoMetaByCustomerNumberAndFileId(customerNumber, photoId)
                .orElseThrow(() -> new RecordNotFoundException("No photo with this parameters found"));

        String responseFileName = photo.getFileName();

        fileOperations.downloadFile(responseFileName);

        Path filePath = this.fileStorageLocation.resolve(responseFileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        // Check if file exists
        if (resource.exists()) {
            return resource;
        } else {
            throw new FileNotFoundException("File not found");
        }
    }

    /**
     * Method to perform file deletion by customer number and photo ID
     *
     * @param customerNumber get customer number from the REST controller
     * @param photoId        get photo IR from the REST controller
     * @return photo DTO as a response
     * @throws IOException
     */
    public PhotoDTO deletePhoto(Long customerNumber, Long photoId) throws IOException {
        Photo photo =
                photoRepository.findPhotoMetaByCustomerNumberAndFileId(customerNumber, photoId)
                        .orElseThrow(() -> new RecordNotFoundException("No photo with this parameters found"));

        PhotoDTO photoDTO = modelMapper.map(photo, PhotoDTO.class);

        String responseFileName = photo.getFileName();

        // Delete photo from temp storage
        Path targetLocation = this.fileStorageLocation.resolve(responseFileName);
        Files.deleteIfExists(targetLocation);

        fileOperations.deleteFile(responseFileName); // Delete file from the FTP server
        photoRepository.deleteFileByCustomerNumberAndFileId(customerNumber, photoId); // Delete file metadata from the database

        return photoDTO;
    }
}
