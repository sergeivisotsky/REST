package org.sergei.rest.persistence.dao.impl;

import org.sergei.rest.persistence.dao.repos.CustomerDAO;
import org.sergei.rest.persistence.pojo.Customer;
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
public class CustomerDAOImpl implements CustomerDAO {

    private static final String SQL_SAVE_CUSTOMER = "INSERT INTO customers(first_name, last_name, age) VALUES(?, ?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM customers";
    private static final String SQL_UPDATE_CUSTOMER = "UPDATE customers SET customer_id = ?, first_name = ?, last_name = ?, age = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM customers WHERE customer_id = ?";
    private static final String SQL_EXISTS_BY_CUSTOMER_ID = "SELECT count(*) FROM customers WHERE customer_id = ?";
    private static final String SQL_DELETE = "DELETE FROM customers WHERE customer_id = ?";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(Customer customer) {
        try {
            jdbcTemplate.update(SQL_SAVE_CUSTOMER, customer.getFirstName(), customer.getLastName(), customer.getAge());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<Customer> findAll() {
        try {
            return jdbcTemplate.query(SQL_FIND_ALL, new CustomerRowMapper());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Customer findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new CustomerRowMapper(), id);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean existsById(Long customerId) {
        int count = jdbcTemplate.queryForObject(SQL_EXISTS_BY_CUSTOMER_ID, new Object[]{customerId}, Integer.class);
        return count > 0;
    }

    @Override
    public void updateRecord(Customer customer) {
        try {
            jdbcTemplate.update(SQL_UPDATE_CUSTOMER, customer.getCustomerId(), customer.getFirstName(),
                    customer.getLastName(), customer.getAge());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void delete(Customer customer) {
        try {
            jdbcTemplate.update(SQL_DELETE, customer.getCustomerId());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }

    private static final class CustomerRowMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer customer = new Customer();

            customer.setCustomerId(rs.getLong("customer_id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setAge(rs.getInt("age"));

            return customer;
        }
    }
}
