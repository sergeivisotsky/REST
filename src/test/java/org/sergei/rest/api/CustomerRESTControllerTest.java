package org.sergei.rest.api;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.sergei.rest.dao.CustomerDAO;
import org.sergei.rest.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ConfigurationProperties("classpath:src/test/resources/REST_API-servlet-test.xml")
@WebAppConfiguration
@AutoConfigureRestDocs
class CustomerRESTControllerTest {

    @MockBean
    private CustomerDAO customerDAO;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() {
        Customer customer = new Customer("John", "Smith", 25);
        customer.setCustomerId(1L);
        when(customerDAO.save(Mockito.any(Customer.class))).thenReturn(customer);
        when(customerDAO.findById(1L)).thenReturn(customer);
    }
}