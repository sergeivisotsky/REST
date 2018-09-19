package org.sergei.rest.api;

import org.codehaus.jackson.map.ObjectMapper;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.sergei.rest.model.Customer;
import org.sergei.rest.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@ConfigurationProperties("classpath:src/test/resources/REST_API-servlet-test.xml")
@WebAppConfiguration
public //@AutoConfigureRestDocs
class CustomerRESTControllerTest {

    /*@Rule
    public RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");*/

    @MockBean
    private CustomerService customerService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
//                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @After
    public void cleanup() {
        EasyMock.verify(customerService);
        EasyMock.reset(customerService);
    }

    @Test
    public void getAllCustomers() throws Exception {
//        Customer customer = new Customer
        EasyMock.expect(customerService.getAllCustomers());

        EasyMock.replay(customerService);

        this.mockMvc.perform(get("/api/v1/customers"));
    }
}