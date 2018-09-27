package org.sergei.rest.service;

import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.model.Product;
import org.sergei.rest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Find all products
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // Find product by product code
    public Product findByCode(String productCode) {
        return productRepository.findByCode(productCode)
                .orElseThrow(() -> new RecordNotFoundException("Product with this code not found"));
    }

    // Save new product
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    // Update product by code
    public Product updateProduct(String productCode, Product productRequest) {
        return productRepository.findByCode(productCode)
                .map(product -> {
                    product.setProductCode(productRequest.getProductCode());
                    product.setProductName(productRequest.getProductName());
                    product.setProductLine(productRequest.getProductLine());
                    product.setProductVendor(productRequest.getProductVendor());
                    product.setPrice(productRequest.getPrice());
                    return productRepository.save(product);
                }).orElseThrow(() -> new RecordNotFoundException("Product with this code not found"));
    }

    // Delete product by code
    public Product deleteProduct(String productCode) {
        return productRepository.findByCode(productCode).map(product -> {
            productRepository.delete(product);
            return product;
        }).orElseThrow(() -> new RecordNotFoundException("Product with this code not found"));
    }
}
