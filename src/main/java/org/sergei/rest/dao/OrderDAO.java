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
    private static final String SQL_FIND_ALL = "SELECT orders.order_number, orders.customer_number, orders.order_date, orders.required_date, " +
            "orders.shipped_date, orders.status, order_details.product_code, order_details.quantity_ordered, order_details.price FROM orders " +
            "INNER JOIN order_details ON orders.order_number = order_details.order_number";

    private static final String SQL_FIND_BY_NUMBER = "SELECT orders.order_number, orders.customer_number, orders.order_date, orders.required_date, " +
            "orders.shipped_date, orders.status, order_details.product_code, order_details.quantity_ordered, order_details.price FROM orders " +
            "INNER JOIN order_details ON orders.order_number = order_details.order_number and orders.order_number = ?";

    private static final String SQL_FIND_ALL_BY_CUSTOMER_NUMBER = "SELECT orders.order_number, orders.customer_number, orders.order_date, orders.required_date, " +
            "orders.shipped_date, orders.status, order_details.product_code, order_details.quantity_ordered, order_details.price FROM orders " +
            "INNER JOIN order_details ON orders.order_number = order_details.order_number AND orders.customer_number = ?";

    private static final String SQL_FIND_BY_CUSTOMER_AND_ORDER_NUMBERS = "SELECT orders.order_number, orders.customer_number, orders.order_date, orders.required_date, " +
            "orders.shipped_date, orders.status, order_details.product_code, order_details.quantity_ordered, order_details.price FROM orders " +
            "INNER JOIN order_details ON orders.order_number = order_details.order_number WHERE orders.customer_number = ? AND orders.order_number = ?";

    private static final String SQL_FIND_BY_PRODUCT_CODE = "SELECT orders.order_number, orders.customer_number, orders.order_date, orders.required_date, " +
            "orders.shipped_date, orders.status, order_details.product_code, order_details.quantity_ordered, order_details.price FROM orders " +
            "INNER JOIN order_details ON orders.order_number = order_details.order_number WHERE order_details.product_code = ?";

    private static final String SQL_SAVE_ORDER = "INSERT INTO orders(order_number, customer_number, order_date, required_date, shipped_date, status) VALUES(?, ?, ?, ?, ?, ?)";

    private static final String SQL_SAVE_ORDER_DETAILS = "INSERT INTO order_details(order_number, product_code, quantity_ordered, price) VALUES (?, ?, ?, ?)";

    private static final String SQL_UPDATE_ORDER = "UPDATE orders SET order_number = ?, customer_number = ?, order_date = ?, required_date = ?, " +
            "shipped_date = ?, status = ? WHERE customer_number = ? AND order_number = ?";

    private static final String SQL_EXISTS_BY_ORDER_NUMBER = "SELECT count(*) FROM orders WHERE order_number = ?";

    private static final String SQL_EXISTS_BY_PRODUCT_CODE = "SELECT count(*) FROM products WHERE product_code = ?";

    private static final String SQL_EXISTS_BY_CUSTOMER_NUMBER = "SELECT count(*) FROM orders WHERE customer_number = ?";

    private static final String SQL_UPDATE_ORDER_DETAILS = "UPDATE order_details SET order_number = ?, product_code  = ?, quantity_ordered = ?, price = ? WHERE order_number = ?";

    private static final String SQL_DELETE_ORDERS_BY_ORDER_NUMBER = "DELETE FROM orders WHERE order_number = ?";

    private static final String SQL_DELETE_ORDERS_BY_CUSTOMER_ORDER_NUMBERS = "DELETE FROM orders WHERE customer_number = ? AND order_number = ?";

    private static final String SQL_DELETE_ORDERS_DETAILS = "DELETE FROM order_details WHERE order_number = ?";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;


    // Find all orders
    public List<Order> findAll() {
        try {
            return jdbcTemplate.query(SQL_FIND_ALL, new OrderRowMapper());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    // Find order by number
    public List<Order> findByNumber(Long orderNumber) {
        try {
            return jdbcTemplate.query(SQL_FIND_BY_NUMBER, new OrderRowMapper(), orderNumber);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    // Find order by customer and order numbers
    public List<Order> findByCustomerAndOrderNumbers(Long customerNumber, Long orderNumber) {
        try {
            return jdbcTemplate.query(SQL_FIND_BY_CUSTOMER_AND_ORDER_NUMBERS, new OrderRowMapper(), customerNumber, orderNumber);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    // Find all orders by the given product number
    public List<Order> findAllByProductCode(String productCode) {
        try {
            return jdbcTemplate.query(SQL_FIND_BY_PRODUCT_CODE, new OrderRowMapper(), productCode);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    // Find all orders by customer number
    public List<Order> findAllByCustomerNumber(Long customerNumber) {
        return jdbcTemplate.query(SQL_FIND_ALL_BY_CUSTOMER_NUMBER, new OrderRowMapper(), customerNumber);
    }

    // Save order
    public void saveOrder(Order order) {
        try {
            jdbcTemplate.update(SQL_SAVE_ORDER, order.getOrderNumber(), order.getCustomerNumber(), order.getOrderDate(),
                    order.getRequiredDate(), order.getShippedDate(), order.getStatus());
            jdbcTemplate.update(SQL_SAVE_ORDER_DETAILS, order.getOrderNumber(), order.getProductCode(),
                    order.getQuantityOrdered(), order.getPrice());
            logger.info("Order entity saved");
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }

    // Checks if order with this number exists
    public boolean existsByNumber(Long orderNumber) {
        int count = jdbcTemplate.queryForObject(SQL_EXISTS_BY_ORDER_NUMBER, new Object[]{orderNumber}, Integer.class);
        return count > 0;
    }

    // Checks if order with this product code
    public boolean existsByProductCode(String productCode) {
        int count = jdbcTemplate.queryForObject(SQL_EXISTS_BY_PRODUCT_CODE, new Object[]{productCode}, Integer.class);
        return count > 0;
    }

    // Checks if order with this customer number exists
    public boolean existsByCustomerNumber(Long customerNumber) {
        int count = jdbcTemplate.queryForObject(SQL_EXISTS_BY_CUSTOMER_NUMBER, new Object[]{customerNumber}, Integer.class);
        return count > 0;
    }

    // Update order data by customer and order numbers
    public void updateRecord(Long customerNumber, Long orderNumber, Order order) {
        try {
            jdbcTemplate.update(SQL_UPDATE_ORDER, order.getOrderNumber(), order.getCustomerNumber(), order.getOrderDate(),
                    order.getRequiredDate(), order.getShippedDate(), order.getStatus(), customerNumber, orderNumber);
            jdbcTemplate.update(SQL_UPDATE_ORDER_DETAILS, order.getOrderNumber(), order.getProductCode(), order.getQuantityOrdered(),
                    order.getPrice(), orderNumber);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }

    // Delete order by order number
    public void deleteByOrderNumber(Long orderNumber) {
        try {
            jdbcTemplate.update(SQL_DELETE_ORDERS_BY_ORDER_NUMBER, orderNumber);
            jdbcTemplate.update(SQL_DELETE_ORDERS_DETAILS, orderNumber);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }

    // Delete order by order and customer numbers
    public void deleteByCustomerAndOrderNumbers(Long customerNumber, Long orderNumber) {
        try {
            jdbcTemplate.update(SQL_DELETE_ORDERS_BY_CUSTOMER_ORDER_NUMBERS, customerNumber, orderNumber);
            jdbcTemplate.update(SQL_DELETE_ORDERS_DETAILS, orderNumber);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }

    private static final class OrderRowMapper implements RowMapper<Order> {

        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();

            order.setOrderNumber(rs.getLong("order_number"));
            order.setCustomerNumber(rs.getLong("customer_number"));
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
