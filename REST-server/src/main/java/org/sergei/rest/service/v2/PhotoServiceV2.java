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

import org.sergei.rest.dto.PhotoDTO;
import org.sergei.rest.exceptions.ResourceNotFoundException;
import org.sergei.rest.model.Customer;
import org.sergei.rest.model.Photo;
import org.sergei.rest.repository.CustomerRepository;
import org.sergei.rest.repository.PhotoRepository;
import org.sergei.rest.service.Constants;
import org.sergei.rest.service.PhotoService;
import org.sergei.rest.util.ObjectMapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * V2 of photo service
 *
 * @author Sergei Visotsky
 */
@Service
public class PhotoServiceV2 extends PhotoService {

    public PhotoServiceV2(PhotoRepository photoRepository, CustomerRepository customerRepository) {
        super(photoRepository, customerRepository);
    }

    /**
     * Method to find all photos by customer number paginated
     *
     * @param customerId get customer number from the REST controller
     * @return list of the photo DTOs as a response
     */
    public Page<PhotoDTO> findAllPaginatedV2(Long customerId, int page, int size) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );
        Page<Photo> photos = photoRepository.findAllPhotosByCustomerIdPaginated(customerId, PageRequest.of(page, size));
        if (photos == null) {
            throw new ResourceNotFoundException("Invalid customer ID or photos not found");
        }

        Page<PhotoDTO> photoDTOS = ObjectMapperUtil.mapAllPages(photos, PhotoDTO.class);
        photoDTOS.forEach(photoDTO -> {
            photoDTO.setCustomerId(customer.getCustomerId());
        });

        return photoDTOS;
    }
}
