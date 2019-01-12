/*
 * Copyright 2018-2019 Sergei Visotsky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.rest.controller.v2;

import io.swagger.annotations.*;
import org.sergei.rest.controller.PhotoController;
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

    private final PhotoServiceV2 photoServiceV2;

    @Autowired
    public PhotoControllerV2(PhotoServiceV2 photoServiceV2) {
        this.photoServiceV2 = photoServiceV2;
    }

    @ApiOperation("Get all photos for the customer paginated")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @GetMapping(value = "/v2/customers/{customerId}/photo", params = {"page", "size"})
    public ResponseEntity<Page<PhotoDTO>> findAllCustomerPhotosPaginated(@ApiParam(value = "Customer ID whose photos should be deleted", required = true)
                                                                         @PathVariable("customerId") Long customerId,
                                                                         @ApiParam("Number of page")
                                                                         @RequestParam("page") int page,
                                                                         @ApiParam("Number of elements per page")
                                                                         @RequestParam("size") int size) {
        return new ResponseEntity<>(photoServiceV2.findAllPaginatedV2(customerId, page, size), HttpStatus.OK);
    }

}
