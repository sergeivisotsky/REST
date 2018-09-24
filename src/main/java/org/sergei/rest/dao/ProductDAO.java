package org.sergei.rest.dao;

import org.sergei.rest.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDAO {

    private static final String SQL_FIND_ALL_PRODUCTS = "SELECT * FROM products";

    private static final String SQL_FIND_PRODUCT_BY_CODE = "SELECT * FROM products WHERE product_code = ?";

    private static final String SQL_SAVE_PRODUCT = "INSERT INTO products(product_code, product_name, product_line, product_vendor, price) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_PRODUCT_BY_CODE = "UPDATE products SET product_code = ?, product_name = ?, product_line = ?, product_vendor = ?, price = ? WHERE product_code = ?";

    private static final String SQL_DELETE_PRODUCT_BY_CODE = "DELETE FROM products WHERE product_code = ?";

    private static final String SQL_EXISTS_BY_CODE = "SELECT count(*) FROM products WHERE product_code = ?";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Find all products
    public List<Product> findAll() {
        try {
            return jdbcTemplate.query(SQL_FIND_ALL_PRODUCTS, new ProductRowMapper());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    // Find product by product code
    public Product findByCode(String productCode) {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_PRODUCT_BY_CODE, new ProductRowMapper(), productCode);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    // Save new product
    public void saveProduct(Product product) {
        try {
            jdbcTemplate.update(SQL_SAVE_PRODUCT, product.getProductCode(), product.getProductName(),
                    product.getProductLine(), product.getProductVendor(), product.getPrice());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }

    // Update product by code
    public void updateProduct(String productCode, Product product) {
        try {
            jdbcTemplate.update(SQL_UPDATE_PRODUCT_BY_CODE, product.getProductCode(), product.getProductName(),
                    product.getProductLine(), product.getProductVendor(), product.getPrice(), productCode);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }

    // Delete product by code
    public void deleteProduct(String productCode) {
        try {
            jdbcTemplate.update(SQL_DELETE_PRODUCT_BY_CODE, productCode);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }

    // Check if product exists by given code
    public boolean existsByProductCode(String productCode) {
        int count = jdbcTemplate.queryForObject(SQL_EXISTS_BY_CODE, new Object[]{productCode}, Integer.class);

        return count > 0;
    }

    private static final class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();

            product.setProductCode(rs.getString("product_code"));
            product.setProductName(rs.getString("product_name"));
            product.setProductLine(rs.getString("product_line"));
            product.setProductVendor(rs.getString("product_vendor"));
            product.setPrice(rs.getBigDecimal("price"));

            return product;
        }
    }
}
