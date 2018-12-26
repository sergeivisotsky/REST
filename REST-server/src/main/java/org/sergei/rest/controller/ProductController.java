package org.sergei.rest.controller;

import io.swagger.annotations.*;
import org.sergei.rest.dto.ProductDTO;
import org.sergei.rest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Api(
        value = "/api/v1/products",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("Get all products")
    @GetMapping("/v1/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @ApiOperation("Get product by code")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid product code")
            }
    )
    @GetMapping("/v1/products/{productCode}")
    public ResponseEntity<ProductDTO> getProductByCode(@ApiParam(value = "Product code which should be found", required = true)
                                                       @PathVariable("productCode") String productCode) {
        return new ResponseEntity<>(productService.findByCode(productCode), HttpStatus.OK);
    }

    @ApiOperation(value = "Add a new product", notes = "Operation allowed for ADMIN only")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = {
            "/v1/products",
            "/v2/products"
    }, consumes = "application/json")
    public ResponseEntity<ProductDTO> saveProduct(@ApiParam(value = "Saved product", required = true)
                                                  @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.save(productDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update product by code", notes = "Operation allowed for ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid product code")
            }
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(value = {
            "/v1/products/{productCode}",
            "/v2/products/{productCode}"
    }, consumes = "application/json")
    public ResponseEntity<ProductDTO> updateProductByCode(@ApiParam(value = "Updated product code", required = true)
                                                          @PathVariable("productCode") String productCode,
                                                          @ApiParam(value = "Updated product", required = true)
                                                          @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.update(productCode, productDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete product by code", notes = "Operation allowed for ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid product code")
            }
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping({
            "/v1/products/{productCode}",
            "/v2/products/{productCode}"
    })
    public ResponseEntity<ProductDTO> deleteProductByCode(@ApiParam(value = "Deleted product", required = true)
                                                          @PathVariable("productCode") String productCode) {
        return new ResponseEntity<>(productService.delete(productCode), HttpStatus.NO_CONTENT);
    }
}
