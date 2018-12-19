package org.sergei.rest.controller.v2;

import io.swagger.annotations.*;
import org.sergei.rest.dto.PhotoDTO;
import org.sergei.rest.service.v2.PhotoServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * V2 of photo controller
 *
 * @author Sergei Visotsky
 */
@Api(
        value = "/api/v2/customers/{customerId}/photos",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class PhotoControllerV2 {

    @Autowired
    private PhotoServiceV2 photoServiceV2;

    @ApiOperation("Get all photos for the customer paginated")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @GetMapping(value = "/v2/customers/{customerId}/photo", params = {"page", "size"})
    public ResponseEntity<Page<PhotoDTO>> findAllCustomerPhotosPaginated(@ApiParam(value = "Customer ID whose photos should be deleted", required = true)
                                                                         @PathVariable("customerId") Long customerId,
                                                                         @ApiParam(value = "Number of page", required = true)
                                                                         @RequestParam("page") int page,
                                                                         @ApiParam(value = "Number of elements per page", required = true)
                                                                         @RequestParam("size") int size) {
        return new ResponseEntity<>(photoServiceV2.findAllPaginatedV2(customerId, page, size), HttpStatus.OK);
    }

}
