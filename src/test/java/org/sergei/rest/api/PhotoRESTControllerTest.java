package org.sergei.rest.api;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
public class PhotoRESTControllerTest {

    private MockMvc mockMvc;

    @Test
    public void testDownloadPhoto() throws Exception {
        mockMvc.perform(get("/api/v1/customers/19/photos/OsJ2K1j5vjI.jpg"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/octet-stream"));
    }

}