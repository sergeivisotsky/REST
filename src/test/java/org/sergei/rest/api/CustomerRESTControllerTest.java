package org.sergei.rest.api;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sergei.rest.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
//@ConfigurationProperties("classpath:src/test/resources/REST_API-servlet-test.xml")
@ContextConfiguration("classpath:REST_API-servlet-test.xml")
@WebAppConfiguration
public class CustomerRESTControllerTest {

    /*@Rule
    public RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");*/
    @Resource
    private WebApplicationContext context;

    private MockMvc mockMvc;

    // FIXME: Not a Mock
    @MockBean
    @Autowired
    private CustomerService customerService;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
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