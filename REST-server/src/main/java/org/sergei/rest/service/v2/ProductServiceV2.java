/*
 * Copyright 2018-2019 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.sergei.rest.service.v2;

import org.sergei.rest.dto.v2.ProductDTOV2;
import org.sergei.rest.exceptions.ResourceNotFoundException;
import org.sergei.rest.model.Product;
import org.sergei.rest.repository.ProductRepository;
import org.sergei.rest.service.Constants;
import org.sergei.rest.service.ProductService;
import org.sergei.rest.util.ObjectMapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return ObjectMapperUtil.mapAll(products, ProductDTOV2.class);
    }

    /**
     * Find all products paginated
     *
     * @return list of found product DTO
     */
    public Page<ProductDTOV2> findAllPaginatedV2(int page, int size) {
        Page<Product> products = productRepository.findAll(PageRequest.of(page, size));
        return ObjectMapperUtil.mapAllPages(products, ProductDTOV2.class);
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
        return ObjectMapperUtil.map(product, ProductDTOV2.class);
    }
}
