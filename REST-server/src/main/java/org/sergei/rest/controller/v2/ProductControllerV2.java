/*
 * Copyright 2018-2019 the original author.
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
import org.sergei.rest.dto.v2.ProductDTOV2;
import org.sergei.rest.service.Constants;
import org.sergei.rest.service.v2.ProductServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.sergei.rest.controller.hateoas.LinkUtil.setLinksForAllProducts;
import static org.sergei.rest.controller.hateoas.LinkUtil.setLinksForProduct;

/**
 * V2 of product controller
 *
 * @author Sergei Visotsky
 */
@Api(
        value = "/api/v2/products",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class ProductControllerV2 {

    private final ProductServiceV2 productServiceV2;

    @Autowired
    public ProductControllerV2(ProductServiceV2 productServiceV2) {
        this.productServiceV2 = productServiceV2;
    }

    @ApiOperation("Get all products")
    @GetMapping("/v2/products")
    public ResponseEntity getAllProductsV2() {
        List<ProductDTOV2> productDTOV2List = productServiceV2.findAllV2();
        return new ResponseEntity<>(setLinksForAllProducts(productDTOV2List), HttpStatus.OK);
    }

    @ApiOperation("Get all products paginated")
    @GetMapping(value = "/v2/products", params = {"page", "size"})
    public ResponseEntity getAllProductsPaginatedV2(@ApiParam("Number of page")
                                                    @RequestParam("page") int page,
                                                    @ApiParam("Number of elements per page")
                                                    @RequestParam("size") int size) {
        Page<ProductDTOV2> productDTOV2List = productServiceV2.findAllPaginatedV2(page, size);
        return new ResponseEntity<>(setLinksForAllProducts(productDTOV2List), HttpStatus.OK);
    }

    @ApiOperation("Get product by code")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = Constants.PRODUCT_NOT_FOUND)
            }
    )
    @GetMapping("/v2/products/{productCode}")
    public ResponseEntity<ProductDTOV2> getProductByCodeV2(@ApiParam(value = "Product code which should be found", required = true)
                                                           @PathVariable("productCode") String productCode) {
        ProductDTOV2 productDTOV2 = productServiceV2.findByCodeV2(productCode);
        return new ResponseEntity<>(setLinksForProduct(productDTOV2), HttpStatus.OK);
    }

    @ApiOperation("Update one or many fields of the product")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = Constants.PRODUCT_NOT_FOUND)
            }
    )
    @PatchMapping("/v2/products/{productCode}")
    public ResponseEntity<ProductDTOV2> patchProduct(@ApiParam(value = "Product code which should be parsed", required = true)
                                                     @PathVariable("productCode") String productCode,
                                                     @RequestBody Map<String, Object> params) {
        ProductDTOV2 productDTOV2 = productServiceV2.patch(productCode, params);
        return new ResponseEntity<>(setLinksForProduct(productDTOV2), HttpStatus.OK);
    }
}
