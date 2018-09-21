package org.sergei.rest.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:REST_API-servlet-test.xml")
@WebAppConfiguration
public class CustomerRESTControllerTest {

    @Resource
    private WebApplicationContext context;

    private MockMvc mockMvc;

    // FIXME: Not a Mock
    /*@MockBean
    @Autowired
    private CustomerService customerService;*/

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    /*@After
    public void cleanup() {
        EasyMock.verify(customerService);
        EasyMock.reset(customerService);
    }*/

    @Test
    public void getAllCustomers() throws Exception {
        this.mockMvc.perform(get("/api/v1/customers").param("testParam", "TestValue"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(document("{class-name}/{method-name}",
                        requestParameters(parameterWithName("firstName").description("Customer first name")),
                        responseFields(fieldWithPath("id").description("Customer ID")
                        )));
    }
}