package org.sergei.rest.controller;

import io.swagger.annotations.*;
import org.sergei.rest.dto.ProductDTO;
import org.sergei.rest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Api(
        value = "/api/v1/products",
        description = "Products API methods",
        produces = "application/json, application/xml",
        consumes = "application/json, application/xml"
)
@RestController
@RequestMapping(value = "/api/v1/products",
        produces = {"application/json", "application/xml"})
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("Get all products")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @ApiOperation("Get product by code")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid product code")
            }
    )
    @GetMapping("/{productCode}")
    public ResponseEntity<ProductDTO> getProductByCode(@ApiParam(value = "Product code which should be found", required = true)
                                                       @PathVariable("productCode") String productCode) {
        return new ResponseEntity<>(productService.findByCode(productCode), HttpStatus.OK);
    }

    @ApiOperation("Add a new product")
    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<ProductDTO> saveProduct(@ApiParam(value = "Saved product", required = true)
                                                  @RequestBody ProductDTO productDTO) {
        productService.save(productDTO);

        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    @ApiOperation("Update product by code")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid product code")
            }
    )
    @PutMapping(value = "/{productCode}", consumes = {"application/json", "application/xml"})
    public ResponseEntity<ProductDTO> updateProductByCode(@ApiParam(value = "Updated product code", required = true)
                                                          @PathVariable("productCode") String productCode,
                                                          @ApiParam(value = "Updated product", required = true)
                                                          @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.update(productCode, productDTO), HttpStatus.ACCEPTED);
    }

    @ApiOperation("Delete product by code")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid product code")
            }
    )
    @DeleteMapping("/{productCode}")
    public ResponseEntity<ProductDTO> deleteProductByCode(@ApiParam(value = "Deleted product", required = true)
                                                          @PathVariable("productCode") String productCode) {
        return new ResponseEntity<>(productService.delete(productCode), HttpStatus.NO_CONTENT);
    }
}
