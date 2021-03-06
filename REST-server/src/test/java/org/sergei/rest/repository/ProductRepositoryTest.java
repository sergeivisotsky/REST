/*
 * Copyright 2018-2019 the original author.
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
