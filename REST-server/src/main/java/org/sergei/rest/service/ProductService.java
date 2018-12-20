package org.sergei.rest.service;

import org.sergei.rest.dto.ProductDTO;
import org.sergei.rest.exceptions.ResourceNotFoundException;
import org.sergei.rest.model.Product;
import org.sergei.rest.repository.ProductRepository;
import org.sergei.rest.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class ProductService {

    protected static final String PRODUCT_NOT_FOUND = "Product with code not found";
    protected final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Find all products
     *
     * @return list of found product DTO
     */
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return ObjectMapperUtil.mapAll(products, ProductDTO.class);
    }

    /**
     * Find product by product code
     *
     * @param productCode by which it should be found
     * @return product DTO
     */
    public ProductDTO findByCode(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(
                        () -> new ResourceNotFoundException(PRODUCT_NOT_FOUND)
                );
        return ObjectMapperUtil.map(product, ProductDTO.class);
    }

    /**
     * Save new product
     *
     * @param productDTO product to be saved
     */
    public ProductDTO save(ProductDTO productDTO) {
        Product product = ObjectMapperUtil.map(productDTO, Product.class);
        Product savedProduct = productRepository.save(product);
        return ObjectMapperUtil.map(savedProduct, ProductDTO.class);
    }

    /**
     * Update product by code
     *
     * @param productCode to be updated
     * @param productDTO  new product content
     * @return updated product
     */
    public ProductDTO update(String productCode, ProductDTO productDTO) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(
                        () -> new ResourceNotFoundException(PRODUCT_NOT_FOUND)
                );

        product.setProductCode(productCode);
        product.setProductName(productDTO.getProductName());
        product.setProductLine(productDTO.getProductLine());
        product.setProductVendor(productDTO.getProductVendor());
        product.setPrice(productDTO.getPrice());

        productRepository.save(product);

        return productDTO;
    }

    /**
     * Delete product by code
     *
     * @param productCode which should be deleted
     * @return deleted product content
     */
    public ProductDTO delete(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(
                        () -> new ResourceNotFoundException(PRODUCT_NOT_FOUND)
                );
        productRepository.delete(product);
        return ObjectMapperUtil.map(product, ProductDTO.class);
    }
}
