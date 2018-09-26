package org.sergei.rest.service;

import org.sergei.rest.exceptions.ResourceNotFoundException;
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
        /*if (!productRepository.existsByProductCode(productCode)) {
            throw new ResourceNotFoundException("Product with this code not found");
        }*/
        return productRepository.findByCode(productCode);
    }

    // Save new product
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    // Update product by code
    public Product updateProduct(String productCode, Product product) {
        /*if (!productRepository.existsByProductCode(productCode)) {
            throw new ResourceNotFoundException("No product with this code found");
        }*/
        productRepository.save(product);

        return product;
    }

    // Delete product by code

    public Product deleteProduct(String productCode) {
        /*if (!productRepository.existsByProductCode(productCode)) {
            throw new ResourceNotFoundException("No product with this code found");
        }*/

        Product product = productRepository.findByCode(productCode);
        productRepository.deleteProductByProductCode(productCode);

        return product;
    }
}
