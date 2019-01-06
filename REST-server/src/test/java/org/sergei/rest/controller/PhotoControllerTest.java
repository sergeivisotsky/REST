package org.sergei.rest.controller;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.sergei.rest.RestServerApplication;
import org.sergei.rest.repository.PhotoRepository;
import org.sergei.rest.testconfig.ResourceServerConfiguration;
import org.sergei.rest.testconfig.WebSecurityConfigTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.nio.file.Paths;

/**
 * Test for {@link PhotoController}
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
public class PhotoControllerTest {

    private static final String BASE_URL = "/api/v1/customers";
    private static final String PHOTO_URI = "/photo";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PhotoRepository photoRepository;

    @Before
    public void setUp() {
        String uploadPath = folder.getRoot().getAbsolutePath() + "/src/test/resources/test/files/storage";
        mvc = MockMvcBuilders
                .standaloneSetup(Paths.get(uploadPath).toAbsolutePath().normalize())
                .build();
    }

    @Test
    public void uploadPhoto_thenGetCreated() {
        String fileName = "test_image.jpg";
        File file = new File(fileName);
        //delete if exits
        file.delete();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("user-file", fileName,
                "text/plain", "test data".getBytes());
    }
}
