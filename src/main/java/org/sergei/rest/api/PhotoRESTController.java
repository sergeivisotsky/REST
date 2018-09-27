package org.sergei.rest.api;

import org.sergei.rest.model.Customer;
import org.sergei.rest.model.PhotoUploadResponse;
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

@RestController
@RequestMapping(value = "/api/v1/customers",
        produces = {"application/json", "application/xml"})
public class PhotoRESTController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PhotoService photoService;

    // The response with all user photos
    @GetMapping("/{customerNumber}/photo")
    public ResponseEntity<List<PhotoUploadResponse>> findAllCustomerPhotos(@PathVariable("customerNumber") Long customerNumber) {
        return new ResponseEntity<>(photoService.findAllUploadedPhotos(customerNumber), HttpStatus.OK);
    }

    // Upload one photo
    @PostMapping("/{customerNumber}/photo")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PhotoUploadResponse uploadPhoto(@PathVariable("customerNumber") Long customerNumber,
                                           @RequestParam("file") CommonsMultipartFile commonsMultipartFile) {
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/customers/" + customerNumber.toString() + "/photo/" + commonsMultipartFile.getOriginalFilename())
                .toUriString();

        return photoService.uploadFileOnTheServer(customerNumber, fileDownloadUri, commonsMultipartFile);
    }

    // Upload multiple photos
    @PostMapping("/{customerNumber}/photos")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<PhotoUploadResponse> uploadMultiplePhotos(@PathVariable("customerNumber") Long customerNumber,
                                                          @RequestParam("files") CommonsMultipartFile[] files) {
        return Arrays.stream(files)
                .map(file -> uploadPhoto(customerNumber, file))
                .collect(Collectors.toList());
    }

    // download photo method by file name
    @GetMapping(value = "/{customerNumber}/photo/{fileName:.+}", produces = {"image/jpeg", "image/png"})
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
    @GetMapping(value = "/{customerNumber}/photos/{photoId}", produces = {"image/jpeg", "image/png"})
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
    @DeleteMapping(value = "/{customerNumber}/photos/{photoId}")
    public ResponseEntity<PhotoUploadResponse> deletePhoto(@PathVariable("customerNumber") Long customerNumber,
                                                           @PathVariable("photoId") Long photoId) throws IOException {

        return new ResponseEntity<>(photoService.deletePhoto(customerNumber, photoId), HttpStatus.OK);
    }
}