package org.sergei.rest.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.rest.model.Customer;
import org.sergei.rest.model.Photo;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link PhotoRepository}
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
public class PhotoRepositoryTest {

    @Autowired
    @Qualifier("customerRepository")
    private CustomerRepository customerRepository;

    @Autowired
    @Qualifier("photoRepository")
    private PhotoRepository photoRepository;

    @Test
    public void assertThatIsEmpty() {
        List<Photo> photoList = photoRepository.findAll();
        assertTrue(photoList.isEmpty());
    }

    @Test
    public void savePhoto_thenGetOk() {
        Customer customer = new Customer("John", "Smith", 25, Collections.emptyList(), Collections.emptyList());
        customerRepository.save(customer);
        Photo photo = new Photo(customer, "testFileName", "http://test.com/test.jpg", "jpg", 120L);
        photoRepository.save(photo);
        Iterable<Photo> foundPhotos = photoRepository.findAll();
        assertThat(foundPhotos).hasSize(1);
        assertThat(foundPhotos).contains(photo);
    }

    @Test
    public void savePhoto_deletePhoto_thenGetOk() {
        Customer customer = new Customer("John", "Smith", 25, Collections.emptyList(), Collections.emptyList());
        customerRepository.save(customer);
        Photo photo = new Photo(customer, "testFileName", "http://test.com/test.jpg", "jpg", 120L);
        photoRepository.save(photo);
        Iterable<Photo> foundPhotos = photoRepository.findAll();
        assertThat(foundPhotos).hasSize(1);
        assertThat(foundPhotos).contains(photo);
        photoRepository.delete(photo);
        Iterable<Photo> foundPhotosAfter = photoRepository.findAll();
        assertThat(foundPhotosAfter).hasSize(0);
    }

    @Test
    public void findAllPhotosByCustomerId_thenGetOk() {
        Customer customer = new Customer("John", "Smith", 25, Collections.emptyList(), Collections.emptyList());
        customerRepository.save(customer);
        Photo photo = new Photo(customer, "testFileName", "http://test.com/test.jpg", "jpg", 120L);
        photoRepository.save(photo);
        Iterable<Photo> foundPhotos = photoRepository.findAllPhotosByCustomerId(customer.getCustomerId());
        assertThat(foundPhotos).hasSize(1);
    }

    @Test
    public void findPhotoByCustomerIdAndFileName_thenGetOk() {
        Customer customer = new Customer("John", "Smith", 25, Collections.emptyList(), Collections.emptyList());
        customerRepository.save(customer);
        Photo photo = new Photo(customer, "testFileName", "http://test.com/test.jpg", "jpg", 120L);
        photoRepository.save(photo);
        Optional<Photo> foundPhoto = photoRepository.findPhotoByCustomerIdAndFileName(customer.getCustomerId(), "testFileName");
        assertThat(foundPhoto).isNotEmpty();
        assertEquals(foundPhoto.get().getCustomer().getCustomerId(), customer.getCustomerId());
        assertEquals(foundPhoto.get().getFileName(), photo.getFileName());
    }

    @Test
    public void findPhotoByCustomerIdAndPhotoId_thenGetOk() {
        Customer customer = new Customer("John", "Smith", 25, Collections.emptyList(), Collections.emptyList());
        customerRepository.save(customer);
        Photo photo = new Photo(customer, "testFileName", "http://test.com/test.jpg", "jpg", 120L);
        photoRepository.save(photo);
        Optional<Photo> foundPhoto = photoRepository.findPhotoByCustomerIdAndFileName(customer.getCustomerId(), "testFileName");
        assertThat(foundPhoto).isNotEmpty();
        assertEquals(foundPhoto.get().getCustomer().getCustomerId(), customer.getCustomerId());
        assertEquals(foundPhoto.get().getPhotoId(), photo.getPhotoId());
    }

    @Test
    public void findFileUrlByCustomerId_thenGetOk() {
        Customer customer = new Customer("John", "Smith", 25, Collections.emptyList(), Collections.emptyList());
        customerRepository.save(customer);
        Photo photo = new Photo(customer, "testFileName", "http://test.com/test.jpg", "jpg", 120L);
        photoRepository.save(photo);
        List<String> fileName = photoRepository.findFileUrlByCustomerId(customer.getCustomerId());
        assertThat(fileName).isNotEmpty();
        assertEquals(photo.getFileUrl(), fileName.get(0));
    }
}
