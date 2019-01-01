package org.sergei.rest.service.v2;

import org.sergei.rest.dto.PhotoDTO;
import org.sergei.rest.exceptions.ResourceNotFoundException;
import org.sergei.rest.model.Photo;
import org.sergei.rest.repository.CustomerRepository;
import org.sergei.rest.repository.PhotoRepository;
import org.sergei.rest.service.PhotoService;
import org.sergei.rest.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
        Page<Photo> photos = photoRepository.findAllPhotosByCustomerIdPaginated(customerId, PageRequest.of(page, size));
        if (photos == null) {
            throw new ResourceNotFoundException("Invalid customer ID or photos not found");
        }

        return ObjectMapperUtil.mapAllPages(photos, PhotoDTO.class);
    }
}
