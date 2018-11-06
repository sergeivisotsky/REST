/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.sergei.rest.dto.PhotoDTO;
import org.sergei.rest.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sergei Visotsky, 2018
 */
@RestController
@RequestMapping(value = "/api/v1",
        produces = {"application/json", "application/xml"})
public class PhotoRESTController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PhotoService photoService;

    // The response with all user photos
    @GetMapping("/customers/{customerNumber}/photo")
    @ApiOperation(value = "Get all photos fot the customer")
    public ResponseEntity<List<PhotoDTO>> findAllCustomerPhotos(@PathVariable("customerNumber") Long customerNumber) {
        return new ResponseEntity<>(photoService.findAll(customerNumber), HttpStatus.OK);
    }

    // Upload one photo
    @PostMapping("/customers/{customerNumber}/photo")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Upload photo for the customer")
    public PhotoDTO uploadPhoto(@PathVariable("customerNumber") Long customerNumber,
                                @RequestParam("file") CommonsMultipartFile commonsMultipartFile) {
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/customers/" + customerNumber.toString() + "/photo/" + commonsMultipartFile.getOriginalFilename())
                .toUriString();

        return photoService.uploadFileByCustomerId(customerNumber, fileDownloadUri, commonsMultipartFile);
    }

    // Upload multiple photos
    @PostMapping("/customers/{customerNumber}/photos")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Upload multiple photo for the customer")
    public List<PhotoDTO> uploadMultiplePhotos(@PathVariable("customerNumber") Long customerNumber,
                                               @RequestParam("files") CommonsMultipartFile[] files) {
        return Arrays.stream(files)
                .map(file -> uploadPhoto(customerNumber, file))
                .collect(Collectors.toList());
    }

    // download photo method by file name
    @GetMapping(value = "/customers/{customerNumber}/photo/{fileName:.+}", produces = {"image/jpeg", "image/png"})
    @ApiOperation(value = "Download photo for the customer by photo name")
    public ResponseEntity<Resource> downloadPhotoByName(@PathVariable("customerNumber") Long customerNumber,
                                                        @PathVariable("fileName") String fileName,
                                                        HttpServletRequest request) throws IOException {
        Resource resource = photoService.downloadFileAsResourceByName(customerNumber, fileName);

        String contentType = null;
        try {
            contentType = request.getSession().getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            LOGGER.info("Could not determine file type");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // download photo method by file name
    @GetMapping(value = "/customers/{customerNumber}/photos/{photoId}", produces = {"image/jpeg", "image/png"})
    @ApiOperation(value = "Download photo by customer number and photo ID")
    public ResponseEntity<Resource> downloadPhotoById(@PathVariable("customerNumber") Long customerNumber,
                                                      @PathVariable("photoId") Long photoId,
                                                      HttpServletRequest request) throws IOException {
        Resource resource = photoService.downloadFileAsResourceByFileId(customerNumber, photoId);

        String contentType = null;
        try {
            contentType = request.getSession().getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            LOGGER.info("Could not determine file type");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // File deletion by name
    @DeleteMapping(value = "/customers/{customerNumber}/photos/{photoId}")
    @ApiOperation(value = "Delete photo by customer number and photo ID")
    public ResponseEntity<PhotoDTO> deletePhotoById(@PathVariable("customerNumber") Long customerNumber,
                                                    @PathVariable("photoId") Long photoId) throws IOException {

        return new ResponseEntity<>(photoService.deleteById(customerNumber, photoId), HttpStatus.NO_CONTENT);
    }
}