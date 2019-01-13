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

package org.sergei.rest.controller;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.rest.RestServerApplication;
import org.sergei.rest.model.Product;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link ProductController}
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
public class ProductControllerTest {

    private static final String BASE_URL = "/api/v1/products";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void getAllRoutes_thenReturnOk() throws Exception {
        final String productCode = "LV_01";
        final String productName = "apples";
        final String productLine = "fruits";
        final String productVendor = "Val Venosta";
        final BigDecimal price = new BigDecimal(1.20);
        setupProduct(productCode, productName, productLine, productVendor, price);

        mvc.perform(
                get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productCode").value(productCode))
                .andExpect(jsonPath("$[0].productName").value(productName))
                .andExpect(jsonPath("$[0].productLine").value(productLine))
                .andExpect(jsonPath("$[0].productVendor").value(productVendor))
                .andExpect(jsonPath("$[0].price").value(1.2));
        productRepository.deleteAll();
    }

    @Test
    public void getOneProduct_thenReturnOk() throws Exception {
        final String productCode = "LV_01";
        final String productName = "apples";
        final String productLine = "fruits";
        final String productVendor = "Val Venosta";
        final BigDecimal price = new BigDecimal(1.20);
        setupProduct(productCode, productName, productLine, productVendor, price);

        mvc.perform(
                get(BASE_URL + "/" + productCode)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productCode").value(productCode))
                .andExpect(jsonPath("$.productName").value(productName))
                .andExpect(jsonPath("$.productLine").value(productLine))
                .andExpect(jsonPath("$.productVendor").value(productVendor))
                .andExpect(jsonPath("$.price").value(1.2));
        productRepository.deleteAll();
    }

    @Test
    public void postProduct_thenReturnCreated() throws Exception {
        final String productCode = "LV_01";
        final String productName = "apples";
        final String productLine = "fruits";
        final String productVendor = "Val Venosta";
        final BigDecimal price = new BigDecimal(1.20);

        JSONObject jsonObject = new JSONObject()
                .put("productCode", productCode)
                .put("productName", productName)
                .put("productLine", productLine)
                .put("productVendor", productVendor)
                .put("price", price);
        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productCode").value(productCode))
                .andExpect(jsonPath("$.productName").value(productName))
                .andExpect(jsonPath("$.productLine").value(productLine))
                .andExpect(jsonPath("$.productVendor").value(productVendor))
                .andExpect(jsonPath("$.price").value(1.2));
        productRepository.deleteAll();
    }

    @Test
    public void postProduct_thenPutProduct_thenReturnOk() throws Exception {
        final String productCode = "LV_01";
        final String productName = "apples";
        final String productLine = "fruits";
        final String productVendor = "Val Venosta";
        final BigDecimal price = new BigDecimal(1.20);

        JSONObject jsonObject = new JSONObject()
                .put("productCode", productCode)
                .put("productName", productName)
                .put("productLine", productLine)
                .put("productVendor", productVendor)
                .put("price", price);
        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productCode").value(productCode))
                .andExpect(jsonPath("$.productName").value(productName))
                .andExpect(jsonPath("$.productLine").value(productLine))
                .andExpect(jsonPath("$.productVendor").value(productVendor))
                .andExpect(jsonPath("$.price").value(1.2));

        final String putProductCode = "LV_02";
        final String putProductName = "grapes";
        final String putProductLine = "fruitsss";
        final String putProductVendor = "Val Venostaaaa";
        final BigDecimal putPrice = new BigDecimal(3.20);
        JSONObject jsonObjectPut = new JSONObject()
                .put("productCode", putProductCode)
                .put("productName", putProductName)
                .put("productLine", putProductLine)
                .put("productVendor", putProductVendor)
                .put("price", putPrice);
        mvc.perform(
                put(BASE_URL + "/" + productCode)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObjectPut.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productCode").value(putProductCode))
                .andExpect(jsonPath("$.productName").value(putProductName))
                .andExpect(jsonPath("$.productLine").value(putProductLine))
                .andExpect(jsonPath("$.productVendor").value(putProductVendor))
                .andExpect(jsonPath("$.price").value(3.20));
        productRepository.deleteAll();
    }

    @Test
    public void postProduct_thenDelete_thenReturnNoContent() throws Exception {
        final String productCode = "LV_01";
        final String productName = "apples";
        final String productLine = "fruits";
        final String productVendor = "Val Venosta";
        final BigDecimal price = new BigDecimal(1.20);

        JSONObject jsonObject = new JSONObject()
                .put("productCode", productCode)
                .put("productName", productName)
                .put("productLine", productLine)
                .put("productVendor", productVendor)
                .put("price", price);
        mvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productCode").value(productCode))
                .andExpect(jsonPath("$.productName").value(productName))
                .andExpect(jsonPath("$.productLine").value(productLine))
                .andExpect(jsonPath("$.productVendor").value(productVendor))
                .andExpect(jsonPath("$.price").value(1.2));

        mvc.perform(delete(BASE_URL + "/" + productCode))
                .andExpect(status().isNoContent());
        productRepository.deleteAll();
    }

    public Product setupProduct(String productCode, String productName,
                                String productLine, String productVendor, BigDecimal price) {
        Product product = new Product(productCode, productName, productLine, productVendor, price);
        return productRepository.save(product);
    }
}
