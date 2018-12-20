package org.sergei.rest.controller;

import io.swagger.annotations.*;
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
 * @author Sergei Visotsky
 */
@Api(
        value = "/api/v1/customers/{customerId}/photos",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class PhotoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhotoController.class);

    @Autowired
    private PhotoService photoService;

    @ApiOperation("Get all photos fot the customer")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @GetMapping({"/v1/customers/{customerId}/photo", "/v2/customers/{customerId}/photo"})
    public ResponseEntity<List<PhotoDTO>> findAllCustomerPhotos(@ApiParam(value = "Customer ID whose photos should be deleted", required = true)
                                                                @PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(photoService.findAll(customerId), HttpStatus.OK);
    }

    @ApiOperation("Upload photo for the customer")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @PostMapping({"/v1/customers/{customerId}/photo", "/v2/customers/{customerId}/photo"})
    @ResponseStatus(HttpStatus.CREATED)
    public PhotoDTO uploadPhoto(@ApiParam(value = "Customer ID who uploads photo", required = true)
                                @PathVariable("customerId") Long customerId,
                                @ApiParam(value = "Uploaded file", required = true)
                                @RequestParam("file") CommonsMultipartFile commonsMultipartFile) {
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/customers/" + customerId.toString() + "/photo/" + commonsMultipartFile.getOriginalFilename())
                .toUriString();

        return photoService.uploadFileByCustomerId(customerId, fileDownloadUri, commonsMultipartFile);
    }

    @ApiOperation("Upload multiple photo for the customer")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @PostMapping({"/v1/customers/{customerId}/photos", "/v2/customers/{customerId}/photos"})
    @ResponseStatus(HttpStatus.CREATED)
    public List<PhotoDTO> uploadMultiplePhotos(@ApiParam(value = "Customer ID who uploads photos", required = true)
                                               @PathVariable("customerId") Long customerId,
                                               @ApiParam(value = "Uploaded files", required = true)
                                               @RequestParam("files") CommonsMultipartFile[] files) {
        return Arrays.stream(files)
                .map(file -> uploadPhoto(customerId, file))
                .collect(Collectors.toList());
    }

    @ApiOperation("Download photo for the customer by photo name")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID or file name")
            }
    )
    @GetMapping(value = {
            "/v1/customers/{customerId}/photo/{fileName:.+}",
            "/v2/customers/{customerId}/photo/{fileName:.+}"},
            produces = {"image/jpeg", "image/png"})
    public ResponseEntity<Resource> downloadPhotoByName(@ApiParam(value = "Customer ID whose photos should be downloaded", required = true)
                                                        @PathVariable("customerId") Long customerId,
                                                        @ApiParam(value = "File name which should be downloaded", required = true)
                                                        @PathVariable("fileName") String fileName,
                                                        HttpServletRequest request) throws IOException {
        Resource resource = photoService.downloadFileAsResourceByName(customerId, fileName);

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

    @ApiOperation("Download photo by customer number and photo ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer or photo ID")
            }
    )
    @GetMapping(value = {
            "/v1/customers/{customerId}/photos/{photoId}",
            "/v2/customers/{customerId}/photos/{photoId}"},
            produces = {"image/jpeg", "image/png"})
    public ResponseEntity<Resource> downloadPhotoById(@ApiParam(value = "Customer ID whose photos should be downloaded", required = true)
                                                      @PathVariable("customerId") Long customerId,
                                                      @ApiParam(value = "Photo ID which should be downloaded", required = true)
                                                      @PathVariable("photoId") Long photoId,
                                                      HttpServletRequest request) throws IOException {
        Resource resource = photoService.downloadFileAsResourceByFileId(customerId, photoId);

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

    @ApiOperation("Delete photo by customer number and photo ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer or photo ID")
            }
    )
    @DeleteMapping({"/v1/customers/{customerId}/photos/{photoId}", "/v2/customers/{customerId}/photos/{photoId}"})
    public ResponseEntity<PhotoDTO> deletePhotoById(@ApiParam(value = "Customer ID whose photos should be deleted", required = true)
                                                    @PathVariable("customerId") Long customerId,
                                                    @ApiParam(value = "Photo ID which should be deleted", required = true)
                                                    @PathVariable("photoId") Long photoId) throws IOException {

        return new ResponseEntity<>(photoService.deleteById(customerId, photoId), HttpStatus.NO_CONTENT);
    }
}