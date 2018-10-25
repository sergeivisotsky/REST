package org.sergei.rest.service;

import org.modelmapper.ModelMapper;
import org.sergei.rest.dao.PhotoDAO;
import org.sergei.rest.dto.PhotoDTO;
import org.sergei.rest.exceptions.FileNotFoundException;
import org.sergei.rest.exceptions.FileStorageException;
import org.sergei.rest.model.Photo;
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
import java.util.LinkedList;
import java.util.List;

@Service
public class PhotoService {

    //    @Value("${file.tmp.path}")
    private static final String UPL_DIR = "D:/Program files/servers/apache-tomcat-9.0.10_API/webapps/media";
    private final ModelMapper modelMapper;
    private final Path fileStorageLocation;
    private final PhotoDAO photoDAO;

    @Autowired
    public PhotoService(ModelMapper modelMapper, PhotoDAO photoDAO) {
        this.modelMapper = modelMapper;
        this.photoDAO = photoDAO;
        this.fileStorageLocation = Paths.get(UPL_DIR).toAbsolutePath().normalize();
    }

    /**
     * Method to find all photos by customer number
     *
     * @param customerNumber get customer number from the REST controller
     * @return list of the photo DTOs as a response
     */
    public List<PhotoDTO> findAll(Long customerNumber) {
        List<PhotoDTO> photoDTOSResponse = new LinkedList<>();

        List<Photo> photos = photoDAO.findAllPhotosByCustomerId(customerNumber);

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
    public PhotoDTO uploadFileByCustomerId(Long customerNumber, String fileDownloadUri,
                                           CommonsMultipartFile commonsMultipartFile) {

        /*customerRepository.findById(customerNumber)
                .orElseThrow(() -> new RecordNotFoundException("Customer with this number not found"));*/
        String fileName = StringUtils.cleanPath(commonsMultipartFile.getOriginalFilename());

        PhotoDTO photoDTOResponse = new PhotoDTO();

        // FIXME: set photo ID properly due to it is null right now
//        photoDTOResponse.setPhotoId();
        photoDTOResponse.setCustomerId(customerNumber);
        photoDTOResponse.setFileName(commonsMultipartFile.getOriginalFilename());
        photoDTOResponse.setFileUrl(fileDownloadUri);
        photoDTOResponse.setFileType(commonsMultipartFile.getContentType());
        photoDTOResponse.setFileSize(commonsMultipartFile.getSize());

        // ModelMapper is used to avoid manual conversion from DTO to entity using setters and getters
        Photo photo = modelMapper.map(photoDTOResponse, Photo.class);

        if (fileDownloadUri.length() > 150) {
            throw new FileStorageException("Too long file name");
        }

        // Filename recreation
        try {
            // Check if file contains inappropriate symbols
            if (fileName.contains("..")) {
                throw new FileStorageException("Invalid path sequence");
            }

            // Store files on the server directory replacing existing if it exists
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(commonsMultipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Save file metadata into a database
            photoDAO.save(photo);

            return photoDTOResponse;
        } catch (IOException e) {
            throw new FileStorageException("Cannot store file");
        }
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
        // Get filename by customer number written in database
        Photo photo = photoDAO.findPhotoByCustomerIdAndFileName(customerNumber, fileName);

//        fileOperations.downloadFile(responseFileName);

        Path filePath = this.fileStorageLocation.resolve(photo.getFileName()).normalize();
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
        Photo photo = photoDAO.findByCustomerIdAndPhotoId(customerNumber, photoId);

        String responseFileName = photo.getFileName();

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
    public PhotoDTO deleteById(Long customerNumber, Long photoId) throws IOException {
        Photo photo = photoDAO.findByCustomerIdAndPhotoId(customerNumber, photoId);

        PhotoDTO photoDTO = modelMapper.map(photo, PhotoDTO.class);

        String responseFileName = photo.getFileName();

        // Delete photo from temp storage
        Path targetLocation = this.fileStorageLocation.resolve(responseFileName);
        Files.deleteIfExists(targetLocation);

        photoDAO.delete(photo); // Delete file metadata from the database

        return photoDTO;
    }
}
