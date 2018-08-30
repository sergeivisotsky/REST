package org.sergei.rest.api;

import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.model.Customer;
import org.sergei.rest.model.PhotoUploadResponse;
import org.sergei.rest.service.CustomerService;
import org.sergei.rest.service.PhotoUploadService;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/customers",
        produces = {"application/json", "application/xml"})
public class CustomerRESTController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PhotoUploadService photoUploadService;

    // Get all customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Get customer by specific ID as a parameter
    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable("customerId") Long customerId) throws RecordNotFoundException {
        return customerService.getCustomerById(customerId);
    }

    // Add a new record
    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity addCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);

        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @PostMapping("/{customerId}/photo")
    public PhotoUploadResponse uploadPhoto(@PathVariable("customerId") Long customerId,
                                           @RequestParam("file") CommonsMultipartFile commonsMultipartFile) {
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/customers/" + customerId.toString() + "/photo/")
                .path(commonsMultipartFile.getOriginalFilename())
                .toUriString();

        return photoUploadService.uploadFileOnTheServer(customerId, fileDownloadUri, commonsMultipartFile);
    }

    // TODO: Download file from the FTP server
    @GetMapping("/{customerId}/photo")
    public ResponseEntity<Resource> downloadPhoto(@PathVariable("customerId") Long customerId, HttpServletRequest request) throws IOException {
        Resource resource = photoUploadService.downloadFileAsResource(customerId);

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

    // Update record
    @PutMapping(value = "/{id}", consumes = {"application/json", "application/xml"})
    public Customer updateRecord(@PathVariable("id") Long id,
                                 @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    // Delete order by specific ID
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCustomerById(@PathVariable("id") Long id) {
        return customerService.deleteCustomerById(id);
    }
}
