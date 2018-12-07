package org.sergei.rest.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.rest.model.Customer;
import org.sergei.rest.model.Order;
import org.sergei.rest.model.OrderDetails;
import org.sergei.rest.model.Product;
import org.sergei.rest.test.config.WebSecurityConfigTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link OrderRepository}
 *
 * @author Sergei Visotsky
 * @since 12/7/2018
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = {WebSecurityConfigTest.class})
@EnableJpaRepositories(basePackages = "org.sergei.rest.repository")
@EntityScan(basePackages = "org.sergei.rest.model")
public class OrderRepositoryTest {

    private static final Date TESTING_DATE = new Date(2018, 11, 12, 16, 25, 44);
    private static final BigDecimal PRICE = new BigDecimal(1200);

    @Autowired
    @Qualifier("customerRepository")
    private CustomerRepository customerRepository;

    @Autowired
    @Qualifier("productRepository")
    private ProductRepository productRepository;

    @Autowired
    @Qualifier("orderRepository")
    private OrderRepository orderRepository;

    @Autowired
    @Qualifier("orderDetailsRepository")
    private OrderDetailsRepository orderDetailsRepository;

    @Test
    public void assertThatIsEmpty() {
        List<Order> orderList = orderRepository.findAll();
        assertTrue(orderList.isEmpty());
    }

    @Test
    public void saveOrder_thanGetOk() {
        Customer customer = new Customer("John", "Smith", 25, Collections.emptyList(), Collections.emptyList());
        customerRepository.save(customer);
        Order order = new Order(customer, TESTING_DATE, TESTING_DATE, TESTING_DATE, "pending");
        orderRepository.save(order);
        Iterable<Order> foundOrders = orderRepository.findAll();
        assertThat(foundOrders).hasSize(1);
        assertThat(foundOrders).contains(order);
    }

    @Test
    public void saveOrderDeleteOrder_thenGetOk() {
        Customer customer = new Customer("John", "Smith", 25, Collections.emptyList(), Collections.emptyList());
        customerRepository.save(customer);
        Order order = new Order(customer, TESTING_DATE, TESTING_DATE, TESTING_DATE, "pending");
        orderRepository.save(order);
        Iterable<Order> foundOrders = orderRepository.findAll();
        assertThat(foundOrders).hasSize(1);
        assertThat(foundOrders).contains(order);
        orderRepository.delete(order);
        Iterable<Order> foundOrderAfter = orderRepository.findAll();
        assertThat(foundOrderAfter).hasSize(0);
    }

    @Test
    public void findOrdersByCustomerId_thenGetOk() {
        Customer customer = new Customer("John", "Smith", 25, Collections.emptyList(), Collections.emptyList());
        customerRepository.save(customer);
        Order order = new Order(customer, TESTING_DATE, TESTING_DATE, TESTING_DATE, "pending");
        orderRepository.save(order);
        Iterable<Order> foundOrders = orderRepository.findAllByCustomerId(customer.getCustomerId());
        assertThat(foundOrders).isNotEmpty();
        assertEquals(customer.getCustomerId(), ((List<Order>) foundOrders).get(0).getCustomer().getCustomerId());
        assertEquals(order.getOrderId(), ((List<Order>) foundOrders).get(0).getOrderId());
    }

    @Ignore
    @Test
    public void findOrdersByProductCode_thenGetOk() {
        Customer customer = new Customer("John", "Smith", 25, Collections.emptyList(), Collections.emptyList());
        customerRepository.save(customer);
        Product product = new Product("Test name", "Testing", "Test case", PRICE);
        product.setProductCode("LV_50");
        productRepository.save(product);
        Order order = new Order(customer, TESTING_DATE, TESTING_DATE, TESTING_DATE, "pending");
        OrderDetails orderDetails = new OrderDetails(product, 5, PRICE, order);
        orderDetailsRepository.save(orderDetails);
        orderRepository.save(order);
        Iterable<Order> foundOrders = orderRepository.findAllByProductCode(order.getOrderDetails().get(0).getProduct().getProductCode());
        assertThat(foundOrders).isNotEmpty();
        assertEquals(customer.getCustomerId(), ((List<Order>) foundOrders).get(0).getCustomer().getCustomerId());
        assertEquals(order.getOrderDetails().get(0).getProduct().getProductCode(),
                ((List<Order>) foundOrders).get(0).getOrderDetails().get(0).getProduct().getProductCode());
    }
}
