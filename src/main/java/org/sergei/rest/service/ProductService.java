package org.sergei.rest.service;

import org.modelmapper.ModelMapper;
import org.sergei.rest.dao.ProductDAO;
import org.sergei.rest.dto.ProductDTO;
import org.sergei.rest.model.Product;
import org.sergei.rest.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ModelMapper modelMapper;
    private final ProductDAO productDAO;

    @Autowired
    public ProductService(ModelMapper modelMapper, ProductDAO productDAO) {
        this.modelMapper = modelMapper;
        this.productDAO = productDAO;
    }

    // Find all products
    public List<ProductDTO> findAll() {
        List<Product> products = productDAO.findAll();
        return ObjectMapperUtils.mapAll(products, ProductDTO.class);
    }

    // Find product by product code
    public ProductDTO findByCode(String productCode) {
        Product product = productDAO.findByCode(productCode);
        return modelMapper.map(product, ProductDTO.class);
    }

    // Save new product
    public void saveProduct(ProductDTO productDTORequest) {
        Product product = modelMapper.map(productDTORequest, Product.class);
        productDAO.save(product);
    }

    // Update product by code
    public ProductDTO updateProduct(String productCode, ProductDTO productDTORequest) {
        Product product = productDAO.findByCode(productCode);

        product.setProductCode(productCode);
        product.setProductName(productDTORequest.getProductName());
        product.setProductLine(productDTORequest.getProductLine());
        product.setProductVendor(productDTORequest.getProductVendor());
        product.setPrice(productDTORequest.getPrice());

        productDAO.update(product);

        return productDTORequest;
    }

    // Delete product by code
    public ProductDTO deleteProduct(String productCode) {
        Product product = productDAO.findByCode(productCode);
        ProductDTO productDTOResponse = modelMapper.map(product, ProductDTO.class);
        productDAO.delete(product);
        return productDTOResponse;
    }
}
