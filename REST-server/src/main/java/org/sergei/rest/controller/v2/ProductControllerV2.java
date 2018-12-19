package org.sergei.rest.controller.v2;

import io.swagger.annotations.*;
import org.sergei.rest.dto.v2.ProductDTOV2;
import org.sergei.rest.service.v2.ProductServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.sergei.rest.controller.util.LinkUtil.setLinksForAllProducts;

/**
 * V2 of product controller
 *
 * @author Sergei Visotsky
 * @since 2.0.2
 * Created on 12/9/2018
 */
@Api(
        value = "/api/v2/products",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class ProductControllerV2 {

    @Autowired
    private ProductServiceV2 productServiceV2;

    @ApiOperation("Get all products")
    @GetMapping("/v2/products")
    public ResponseEntity getAllProductsV2() {
        List<ProductDTOV2> productDTOV2List = productServiceV2.findAllV2();
        return new ResponseEntity<>(setLinksForAllProducts(productDTOV2List), HttpStatus.OK);
    }

    @ApiOperation("Get all products paginated")
    @GetMapping(value = "/v2/products", params = {"page", "size"})
    public ResponseEntity getAllProductsPaginatedV2(@ApiParam(value = "Number of page", required = true)
                                                    @RequestParam("page") int page,
                                                    @ApiParam(value = "Number of elements per page", required = true)
                                                    @RequestParam("size") int size) {
        Page<ProductDTOV2> productDTOV2List = productServiceV2.findAllPaginatedV2(page, size);
        return new ResponseEntity<>(setLinksForAllProducts(productDTOV2List), HttpStatus.OK);
    }

    @ApiOperation("Get product by code")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid product code")
            }
    )
    @GetMapping("/v2/products/{productCode}")
    public ResponseEntity<ProductDTOV2> getProductByCodeV2(@ApiParam(value = "Product code which should be found", required = true)
                                                           @PathVariable("productCode") String productCode) {
        ProductDTOV2 productDTOV2 = productServiceV2.findByCodeV2(productCode);
        Link link = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(ProductControllerV2.class)
                        .getProductByCodeV2(productDTOV2.getProductCode())).withSelfRel();
        productDTOV2.add(link);
        return new ResponseEntity<>(productDTOV2, HttpStatus.OK);
    }
}
