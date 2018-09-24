package org.sergei.rest.service;

import org.sergei.rest.dao.ProductDAO;
import org.sergei.rest.exceptions.ResourceNotFoundException;
import org.sergei.rest.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDAO;

    // Find all products
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    // Find product by product code
    public Product findByCode(String productCode) {
        if (!productDAO.existsByProductCode(productCode)) {
            throw new ResourceNotFoundException("Product with this code not found");
        }
        return productDAO.findByCode(productCode);
    }

    // Save new product
    public void saveProduct(Product product) {
        productDAO.saveProduct(product);
    }

    // Update product by code
    public Product updateProduct(String productCode, Product product) {
        if (!productDAO.existsByProductCode(productCode)) {
            throw new ResourceNotFoundException("No product with this code found");
        }
        productDAO.updateProduct(productCode, product);

        return product;
    }

    // Delete product by code

    public Product deleteProduct(String productCode) {
        if (!productDAO.existsByProductCode(productCode)) {
            throw new ResourceNotFoundException("No product with this code found");
        }

        Product product = productDAO.findByCode(productCode);
        productDAO.deleteProduct(productCode);

        return product;
    }
}
