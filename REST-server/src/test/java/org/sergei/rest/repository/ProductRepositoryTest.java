package org.sergei.rest.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link ProductRepository}
 *
 * @author Sergei Visotsky
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = {WebSecurityConfigTest.class})
@EnableJpaRepositories(basePackages = "org.sergei.rest.repository")
@EntityScan(basePackages = "org.sergei.rest.model")
public class ProductRepositoryTest {

    private static final BigDecimal PRICE = new BigDecimal(1200);

    @Autowired
    @Qualifier("productRepository")
    private ProductRepository productRepository;

    @Test
    public void assertThatIsEmpty() {
        List<Product> productList = productRepository.findAll();
        assertTrue(productList.isEmpty());
    }

    @Ignore
    @Test
    public void saveProductThanGetOk() {
        Product product = new Product("LV_01", "Test name", "Testing", "Test case", PRICE);
        product.setProductCode("LV_50");
        productRepository.save(product);
        Iterable<Product> foundProducts = productRepository.findAll();
        assertThat(foundProducts).hasSize(1);
        assertThat(foundProducts).contains(product);
    }
}
