package org.sergei.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.sergei.rest.dto.ProductDTO;
import org.sergei.rest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1",
        produces = {"application/json", "application/xml"})
public class ProductRESTController {

    @Autowired
    private ProductService productService;

    // Find all products
    @GetMapping("/products")
    @ApiOperation(value = "Get all products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    // Find product by product code
    @GetMapping("/products/{productCode}")
    @ApiOperation(value = "Get product by code")
    public ResponseEntity<ProductDTO> getProductByCode(@PathVariable("productCode") String productCode) {
        return new ResponseEntity<>(productService.findByCode(productCode), HttpStatus.OK);
    }

    // Save new product
    @PostMapping(value = "/products",
            consumes = {"application/json", "application/xml"})
    @ApiOperation(value = "Add a new product")
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO) {
        productService.saveProduct(productDTO);

        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    // Update product by code
    @PutMapping(value = "/products/{productCode}",
            consumes = {"application/json", "application/xml"})
    @ApiOperation(value = "Update product by code")
    public ResponseEntity<ProductDTO> updateProductByCode(@PathVariable("productCode") String productCode,
                                                          @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.updateProduct(productCode, productDTO), HttpStatus.ACCEPTED);
    }

    // Delete product by code
    @DeleteMapping("/products/{productCode}")
    @ApiOperation(value = "Delete product by code")
    public ResponseEntity<ProductDTO> deleteProductByCode(@PathVariable("productCode") String productCode) {
        return new ResponseEntity<>(productService.deleteProduct(productCode), HttpStatus.OK);
    }
}
