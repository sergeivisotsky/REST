package org.sergei.rest.api;

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

@RestController
@RequestMapping(value = "/api/v1/customers",
        produces = {"application/json", "application/xml"})
public class PhotoRESTController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PhotoService photoService;

    // Upload photo method
    @RequestMapping(value = "/{customerId}/photo", method = RequestMethod.POST)
    public PhotoUploadResponse uploadPhoto(@PathVariable("customerId") Long customerId,
                                           @RequestParam("file") CommonsMultipartFile commonsMultipartFile) {
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/customers/" + customerId.toString() + "/photo/" + commonsMultipartFile.getOriginalFilename())
                .toUriString();

        return photoService.uploadFileOnTheServer(customerId, fileDownloadUri, commonsMultipartFile);
    }

    // download photo method
    @RequestMapping(value = "/{customerId}/photo", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadPhoto(@PathVariable("customerId") Long customerId,
                                                  HttpServletRequest request) throws IOException {
        Resource resource = photoService.downloadFileAsResource(customerId);

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

    @RequestMapping(value = "/{customerId}/photo", method = RequestMethod.DELETE)
    public ResponseEntity deletePhoto(@PathVariable("customerId") Long customerId) throws IOException {
        return photoService.deletePhoto(customerId);
    }
}
