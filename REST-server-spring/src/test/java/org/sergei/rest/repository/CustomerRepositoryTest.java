package org.sergei.rest.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.rest.model.Customer;
import org.sergei.rest.testconfig.WebSecurityConfigTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link CustomerRepository}
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
public class CustomerRepositoryTest {

    @Autowired
    @Qualifier("customerRepository")
    private CustomerRepository customerRepository;

    @Test
    public void assertThatIsEmpty() {
        List<Customer> customerList = customerRepository.findAll();
        assertTrue(customerList.isEmpty());
    }

    @Test
    public void saveCustomer_thenGetOk() {
        Customer customer = new Customer("John", "Smith", 25, Collections.emptyList(), Collections.emptyList());
        customerRepository.save(customer);
        Iterable<Customer> foundCustomers = customerRepository.findAll();
        assertThat(foundCustomers).hasSize(1);
        assertThat(foundCustomers).contains(customer);
    }

    @Test
    public void saveCustomer_deleteCustomer_thenGetOk() {
        Customer customer = new Customer("John", "Smith", 25, Collections.emptyList(), Collections.emptyList());
        customerRepository.save(customer);
        Iterable<Customer> foundCustomers = customerRepository.findAll();
        assertThat(foundCustomers).hasSize(1);
        assertThat(foundCustomers).contains(customer);
        customerRepository.delete(customer);
        Iterable<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(0);
    }
}
