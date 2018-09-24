package org.sergei.rest.dao;

import org.sergei.rest.model.Customer;
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
public class CustomerDAO {
    private static final String SQL_SAVE_CUSTOMER = "INSERT INTO customers(first_name, last_name, age) VALUES(?, ?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM customers";
    private static final String SQL_UPDATE_CUSTOMER = "UPDATE customers SET first_name = ?, last_name = ?, age = ? WHERE customer_number = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM customers WHERE customer_number = ?";
    private static final String SQL_EXISTS_BY_CUSTOMER_ID = "SELECT count(*) FROM customers WHERE customer_number = ?";
    private static final String SQL_DELETE = "DELETE FROM customers WHERE customer_number = ?";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Save customer
    public void save(Customer customer) {
        try {
            jdbcTemplate.update(SQL_SAVE_CUSTOMER, customer.getCustomerNumber(), customer.getFirstName(),
                    customer.getLastName(), customer.getAge());
            logger.info("Customer entity saved");
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }

    // Find all customers
    public List<Customer> findAll() {
        try {
            return jdbcTemplate.query(SQL_FIND_ALL, new CustomerRowMapper());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    // Find customer by number
    public Customer findByNumber(Long customerNumber) {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new CustomerRowMapper(), customerNumber);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    // Check if customer exists by the given number
    public boolean existsByNumber(Long customerNumber) {
        int count = jdbcTemplate.queryForObject(SQL_EXISTS_BY_CUSTOMER_ID, new Object[]{customerNumber}, Integer.class);
        return count > 0;
    }

    // Update customer data by number
    public void updateRecord(Customer customer, Long customerNumber) {
        try {
            jdbcTemplate.update(SQL_UPDATE_CUSTOMER, customer.getFirstName(),
                    customer.getLastName(), customer.getAge(), customerNumber);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }

    // Delete customer
    public void delete(Customer customer) {
        try {
            jdbcTemplate.update(SQL_DELETE, customer.getCustomerNumber());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }

    private static final class CustomerRowMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer customer = new Customer();

            customer.setCustomerNumber(rs.getLong("customer_number"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setAge(rs.getInt("age"));

            return customer;
        }
    }
}
