/*
 * Copyright 2018-2019 Sergei Visotsky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.rest.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.rest.model.Customer;
import org.sergei.rest.model.Order;
import org.sergei.rest.model.OrderDetails;
import org.sergei.rest.model.Product;
import org.sergei.rest.testconfig.WebSecurityConfigTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link OrderRepository}
 *
 * @author Sergei Visotsky
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = {WebSecurityConfigTest.class})
@EnableJpaRepositories(basePackages = "org.sergei.rest.repository")
@EntityScan(basePackages = "org.sergei.rest.model")
public class OrderRepositoryTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final LocalDateTime TESTING_DATE = LocalDateTime.parse("2018-09-09 09:24:00", FORMATTER);
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
        Product product = new Product("LV_50", "Test name", "Testing", "Test case", PRICE);
        productRepository.save(product);
        Order order = new Order(customer, TESTING_DATE, TESTING_DATE, TESTING_DATE, "pending");
        orderRepository.save(order);
        OrderDetails orderDetails = new OrderDetails(product, 5, PRICE, order);
        orderDetailsRepository.save(orderDetails);
        Iterable<Order> foundOrders = orderRepository.findAllByProductCode(order.getOrderDetails().get(0).getProduct().getProductCode());
        assertThat(foundOrders).isNotEmpty();
        assertEquals(customer.getCustomerId(), ((List<Order>) foundOrders).get(0).getCustomer().getCustomerId());
        assertEquals(order.getOrderDetails().get(0).getProduct().getProductCode(),
                ((List<Order>) foundOrders).get(0).getOrderDetails().get(0).getProduct().getProductCode());
    }
}
