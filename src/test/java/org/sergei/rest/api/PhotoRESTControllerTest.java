package org.sergei.rest.api;

import com.thetransactioncompany.cors.CORSFilter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.TestClass;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sergei.rest.service.PhotoService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(Suite.class)
@ContextConfiguration(classes = {TestContext.class})
@Suite.SuiteClasses({TestClass.class})
@WebAppConfiguration
public class PhotoRESTControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PhotoService photoService;

    @InjectMocks
    private PhotoRESTController photoRESTController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(photoRESTController)
                .addFilters(new CORSFilter())
                .build();
    }

    /*@Test
    public void testDownloadPhoto() throws Exception {
        mockMvc.perform(get("/api/v1/customers/19/photos/OsJ2K1j5vjI.jpg"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/octet-stream"));
    }*/

}