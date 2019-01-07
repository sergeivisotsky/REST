package org.sergei.rest.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.rest.RestServerApplication;
import org.sergei.rest.model.Customer;
import org.sergei.rest.model.Order;
import org.sergei.rest.model.OrderDetails;
import org.sergei.rest.model.Product;
import org.sergei.rest.repository.CustomerRepository;
import org.sergei.rest.repository.OrderRepository;
import org.sergei.rest.repository.ProductRepository;
import org.sergei.rest.testconfig.ResourceServerConfiguration;
import org.sergei.rest.testconfig.WebSecurityConfigTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link OrderController}
 *
 * @author Sergei Visotsky
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestServerApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
@ContextConfiguration(classes = {ResourceServerConfiguration.class, WebSecurityConfigTest.class})
@EnableJpaRepositories(basePackages = "org.sergei.rest.repository")
@EntityScan(basePackages = "org.sergei.rest.model")
public class OrderControllerTest {

    private static final String BASE_URL = "/api/v1/customers";
    private static final String ORDER_URI = "/orders";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void findAllOrders_thenReturnOk() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;
        Customer customer = setupCustomer(firstName, lastName, age);

        final String productCode = "LV_01";
        final String productName = "apples";
        final String productLine = "fruits";
        final String productVendor = "Val Venosta";
        final BigDecimal price = new BigDecimal(1.20);
        Product product = setupProduct(productCode, productName, productLine, productVendor, price);

        final LocalDateTime orderDate = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final LocalDateTime requiredDate = LocalDateTime.parse("2018-09-29T22:00:00", FORMATTER);
        final LocalDateTime shippedDate = LocalDateTime.parse("2018-09-30T22:00:00", FORMATTER);
        final String status = "pending";
        Order order = new Order(customer, orderDate, requiredDate, shippedDate, status);

        List<OrderDetails> orderDetails = new LinkedList<>();
        final int quantityOrdered = 1;
        final BigDecimal orderPrice = new BigDecimal(1.20);
        orderDetails.add(new OrderDetails(product, quantityOrdered, orderPrice, order));
        order.setOrderDetails(orderDetails);

        setupOrder(customer, orderDate, requiredDate, shippedDate, status);

        mvc.perform(
                get(BASE_URL + "/" + customer.getCustomerId() + ORDER_URI)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").isNotEmpty())
                .andExpect(jsonPath("$[0].customerId").isNotEmpty())
                .andExpect(jsonPath("$[0].orderDate").value("2018-09-28"))
                .andExpect(jsonPath("$[0].requiredDate").value("2018-09-29"))
                .andExpect(jsonPath("$[0].shippedDate").value("2018-09-30"))
                .andExpect(jsonPath("$[0].status").value(status))
                .andExpect(jsonPath("$[0].orderDetails").isEmpty())
                /*.andExpect(jsonPath("$[0].orderDetails[0].productCode").value(productCode))
                .andExpect(jsonPath("$[0].orderDetails[0].quantityOrdered").value(quantityOrdered))
                .andExpect(jsonPath("$[0].orderDetails[0].price").value(orderPrice))*/;

    }

    @Ignore
    @Test
    public void findOneOrder_thenReturnOk() throws Exception {
        final String firstName = "John";
        final String lastName = "Smith";
        final int age = 20;
        Customer customer = setupCustomer(firstName, lastName, age);

        final String productCode = "LV_01";
        final String productName = "apples";
        final String productLine = "fruits";
        final String productVendor = "Val Venosta";
        final BigDecimal price = new BigDecimal(1.20);
        Product product = setupProduct(productCode, productName, productLine, productVendor, price);

        final LocalDateTime orderDate = LocalDateTime.parse("2018-09-28T22:00:00", FORMATTER);
        final LocalDateTime requiredDate = LocalDateTime.parse("2018-09-29T22:00:00", FORMATTER);
        final LocalDateTime shippedDate = LocalDateTime.parse("2018-09-30T22:00:00", FORMATTER);
        final String status = "pending";
        Order order = new Order(customer, orderDate, requiredDate, shippedDate, status);

        List<OrderDetails> orderDetails = new LinkedList<>();
        final int quantityOrdered = 1;
        final BigDecimal orderPrice = new BigDecimal(1.20);
        orderDetails.add(new OrderDetails(product, quantityOrdered, orderPrice, order));
        order.setOrderDetails(orderDetails);

        setupOrder(customer, orderDate, requiredDate, shippedDate, status);

        mvc.perform(
                get(BASE_URL + "/" + customer.getCustomerId() + ORDER_URI)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..orderId").isNotEmpty())
                .andExpect(jsonPath("$..customerId").isNotEmpty())
                .andExpect(jsonPath("$..orderDate").value("2018-09-28"))
                .andExpect(jsonPath("$..requiredDate").value("2018-09-29"))
                .andExpect(jsonPath("$..shippedDate").value("2018-09-30"))
                .andExpect(jsonPath("$..status").value(status))
                .andExpect(jsonPath("$.orderDetails").isEmpty())
                /*.andExpect(jsonPath("$[0].orderDetails[0].productCode").value(productCode))
                .andExpect(jsonPath("$[0].orderDetails[0].quantityOrdered").value(quantityOrdered))
                .andExpect(jsonPath("$[0].orderDetails[0].price").value(orderPrice))*/;

    }

    private Order setupOrder(Customer customer, LocalDateTime orderDate,
                             LocalDateTime requiredDate, LocalDateTime shippedDate, String status) {

        Order order = new Order(customer, orderDate, requiredDate, shippedDate, status);
        return orderRepository.save(order);
    }

    private Product setupProduct(String productCode, String productName,
                                 String productLine, String productVendor, BigDecimal price) {
        Product product = new Product(productCode, productName, productLine, productVendor, price);
        return productRepository.save(product);
    }

    private Customer setupCustomer(String firstName, String lastName, int age) {
        Customer customer = new Customer();

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAge(age);

        customerRepository.save(customer);

        return customer;
    }
}
