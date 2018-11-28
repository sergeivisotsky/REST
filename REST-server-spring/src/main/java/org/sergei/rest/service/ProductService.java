package org.sergei.rest.service;

import org.modelmapper.ModelMapper;
import org.sergei.rest.dao.ProductRepository;
import org.sergei.rest.dto.ProductDTO;
import org.sergei.rest.exceptions.ResourceNotFoundException;
import org.sergei.rest.model.Product;
import org.sergei.rest.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class ProductService {

    private static final String PRODUCT_NOT_FOUND = "Product with code not found";

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ModelMapper modelMapper, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    // Find all products
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return ObjectMapperUtils.mapAll(products, ProductDTO.class);
    }

    // Find product by product code
    public ProductDTO findByCode(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product with this code not found")
                );
        return modelMapper.map(product, ProductDTO.class);
    }

    // Save new product
    public void save(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        productRepository.save(product);
    }

    // Update product by code
    public ProductDTO update(String productCode, ProductDTO productDTO) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product with this code not found")
                );

        product.setProductCode(productCode);
        product.setProductName(productDTO.getProductName());
        product.setProductLine(productDTO.getProductLine());
        product.setProductVendor(productDTO.getProductVendor());
        product.setPrice(productDTO.getPrice());

        productRepository.save(product);

        return productDTO;
    }

    // Delete product by code
    public ProductDTO delete(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product with this code not found")
                );
        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }
}
