package org.sergei.rest.dao;

import org.sergei.rest.model.Order;
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
public class OrderDAO {
    private static final String SQL_FIND_ALL = "SELECT orders.order_id, orders.customer_id, orders.order_date, orders.required_date, " +
            "orders.shipped_date, orders.status, order_details.product_code, order_details.quantity_ordered, order_details.price FROM orders " +
            "INNER JOIN order_details ON orders.order_id = order_details.order_id";
    private static final String SQL_FIND_BY_ID = "SELECT orders.order_id, orders.customer_id, orders.order_date, orders.required_date, " +
            "orders.shipped_date, orders.status, order_details.product_code, order_details.quantity_ordered, order_details.price FROM orders " +
            "INNER JOIN order_details ON orders.order_id = order_details.order_id and orders.order_id = ?";
    private static final String SQL_FIND_ALL_BY_CUSTOMER_ID = "SELECT orders.order_id, orders.customer_id, orders.order_date, orders.required_date, " +
            "orders.shipped_date, orders.status, order_details.product_code, order_details.quantity_ordered, order_details.price FROM orders " +
            "INNER JOIN order_details ON orders.order_id = order_details.order_id AND orders.customer_id = ?";
    private static final String SQL_FIND_BY_CUSTOMER_ID_AND_ORDER_ID = "SELECT orders.order_id, orders.customer_id, orders.order_date, orders.required_date, " +
            "orders.shipped_date, orders.status, order_details.product_code, order_details.quantity_ordered, order_details.price FROM orders " +
            "INNER JOIN order_details ON orders.order_id = order_details.order_id WHERE orders.customer_id = ? AND orders.order_id = ?";
    private static final String SQL_FIND_BY_PRODUCT_CODE = "SELECT orders.order_id, orders.customer_id, orders.order_date, orders.required_date, " +
            "orders.shipped_date, orders.status, order_details.product_code, order_details.quantity_ordered, order_details.price FROM orders " +
            "INNER JOIN order_details ON orders.order_id = order_details.order_id WHERE order_details.product_code = ?";
    private static final String SQL_SAVE_ORDER = "INSERT INTO orders(order_id, customer_id, order_date, required_date, shipped_date, status) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_ORDER = "UPDATE orders SET trans_id = ?, product = ?, product_weight = ?, price = ? " +
            "WHERE customer_id = ? AND order_id = ?";
    private static final String SQL_EXISTS_BY_ORDER_ID = "SELECT count(*) FROM orders WHERE order_id = ?";
    private static final String SQL_EXISTS_BY_PRODUCT_CODE = "SELECT count(*) FROM products WHERE product_code = ?";
    private static final String SQL_EXISTS_BY_CUSTOMER_ID = "SELECT count(*) FROM orders WHERE customer_id = ?";
    private static final String SQL_DELETE = "DELETE FROM orders WHERE order_id = ?";

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Order> findAll() {
        try {
            return jdbcTemplate.query(SQL_FIND_ALL, new OrderRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public List<Order> findById(Long id) {
        try {
            return jdbcTemplate.query(SQL_FIND_BY_ID, new OrderRowMapper(), id);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public List<Order> findByCustomerIdAndOrderId(Long customerId, Long orderId) {
        try {
            return jdbcTemplate.query(SQL_FIND_BY_CUSTOMER_ID_AND_ORDER_ID, new OrderRowMapper(), customerId, orderId);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public List<Order> findAllByProductCode(String product_code) {
        try {
            return jdbcTemplate.query(SQL_FIND_BY_PRODUCT_CODE, new OrderRowMapper(), product_code);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public void saveOrder(Long customerId, Order order) {
        try {
            jdbcTemplate.update(SQL_SAVE_ORDER, customerId);
            LOGGER.info("Order entity saved");
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public boolean existsById(Long orderId) {
        int count = jdbcTemplate.queryForObject(SQL_EXISTS_BY_ORDER_ID, new Object[]{orderId}, Integer.class);
        return count > 0;
    }

    public boolean existsByProductCode(String productCode) {
        int count = jdbcTemplate.queryForObject(SQL_EXISTS_BY_PRODUCT_CODE, new Object[]{productCode}, Integer.class);
        return count > 0;
    }

    public boolean existsByCustomerId(Long customerId) {
        int count = jdbcTemplate.queryForObject(SQL_EXISTS_BY_CUSTOMER_ID, new Object[]{customerId}, Integer.class);
        return count > 0;
    }

    /*public void updateRecord(Long customerId, Long orderId, Order order) {
        try {
            jdbcTemplate.update(SQL_UPDATE_ORDER, order.getPrice(), customerId, orderId);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
        }
    }*/

    public void delete(Order order) {
        try {
            jdbcTemplate.update(SQL_DELETE, order.getOrderId());
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public List<Order> findAllByCustomerId(Long id) {
        return jdbcTemplate.query(SQL_FIND_ALL_BY_CUSTOMER_ID, new OrderRowMapper(), id);
    }

    private static final class OrderRowMapper implements RowMapper<Order> {

        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();

            order.setOrderId(rs.getLong("order_id"));
            order.setCustomerId(rs.getLong("customer_id"));
            order.setOrderDate(rs.getDate("order_date"));
            order.setRequiredDate(rs.getDate("required_date"));
            order.setShippedDate(rs.getDate("shipped_date"));
            order.setStatus(rs.getString("status"));
            order.setProductCode(rs.getString("product_code"));
            order.setQuantityOrdered(rs.getInt("quantity_ordered"));
            order.setPrice(rs.getBigDecimal("price"));
            /*List<OrderDetails> orderDetailsList = new LinkedList<>();
            OrderDetails orderDetails;

            while (rs.next()) {
                orderDetails = new OrderDetails();

                orderDetails.setProductCode(rs.getString("product_code"));
                orderDetails.setQuantityOrdered(rs.getInt("quantity_ordered"));
                orderDetails.setPrice(rs.getBigDecimal("price"));

                orderDetailsList.add(orderDetails);
            }

            order.setOrderDetails(orderDetailsList);*/

            return order;
        }
    }
}
