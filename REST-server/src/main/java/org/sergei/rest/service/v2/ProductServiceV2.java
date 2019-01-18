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

package org.sergei.rest.service.v2;

import org.sergei.rest.dto.v2.ProductDTOV2;
import org.sergei.rest.exceptions.ResourceNotFoundException;
import org.sergei.rest.model.Product;
import org.sergei.rest.repository.ProductRepository;
import org.sergei.rest.service.Constants;
import org.sergei.rest.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.sergei.rest.util.ObjectMapperUtil.*;

/**
 * V2 of product service
 *
 * @author Sergei Visotsky
 */
@Service
public class ProductServiceV2 extends ProductService {

    public ProductServiceV2(ProductRepository productRepository) {
        super(productRepository);
    }

    /**
     * Find all products
     *
     * @return list of found product DTO
     */
    public List<ProductDTOV2> findAllV2() {
        List<Product> products = productRepository.findAll();
        return mapAll(products, ProductDTOV2.class);
    }

    /**
     * Find all products paginated
     *
     * @return list of found product DTO
     */
    public Page<ProductDTOV2> findAllPaginatedV2(int page, int size) {
        Page<Product> products = productRepository.findAll(PageRequest.of(page, size));
        return mapAllPages(products, ProductDTOV2.class);
    }

    /**
     * Find product by product code
     *
     * @param productCode by which it should be found
     * @return product DTO
     */
    public ProductDTOV2 findByCodeV2(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Constants.PRODUCT_NOT_FOUND)
                );
        return map(product, ProductDTOV2.class);
    }

    /**
     * Method to patch product (e.g. update obne or many fields)
     *
     * @param productCode which should be patched
     * @param params      parameters that should be patched
     * @return patched product entity as a JSON response
     */
    public ProductDTOV2 patch(String productCode, Map<String, Object> params) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Constants.PRODUCT_NOT_FOUND)
                );
        if (params.get("productName") != null) {
            product.setProductName(String.valueOf(params.get("productName")));
        }
        if (params.get("productLine") != null) {
            product.setProductLine(String.valueOf(params.get("productLine")));
        }
        if (params.get("productVendor") != null) {
            product.setProductVendor(String.valueOf(params.get("productVendor")));
        }
        if (params.get("price") != null) {
            product.setPrice(BigDecimal.valueOf(Long.parseLong(String.valueOf(params.get("price")))));
        }
        return map(productRepository.save(product), ProductDTOV2.class);
    }
}
