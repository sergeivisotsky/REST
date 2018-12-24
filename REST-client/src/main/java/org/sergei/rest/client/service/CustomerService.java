package org.sergei.rest.client.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.sergei.rest.client.model.AuthTokenInfo;
import org.sergei.rest.client.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * @author Sergei Visotsky
 */
@Service
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private static final String REST_RESOURCE_URI = "http://localhost:9091/api/v2";
    private static final String AUTH_SERVER = "http://localhost:9091/oauth/token";
    private static final String PASSWORD_GRANT = "?grant_type=password";
    private static final String USERNAME = "&username=";
    private static final String PASSWORD = "&password=";
    private static final String ACCESS_TOKEN = "?access_token=";

    @Value("${oauth.username}")
    private String usernameValue;

    @Value("${oauth.password}")
    private String passwordValue;

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    @Autowired
    public CustomerService(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
    }

    /**
     * Prepare HTTP Headers
     *
     * @return return headers
     */
    private HttpHeaders getHeaders() {
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

    /**
     * Add HTTP Authorization header, using Basic-Authentication to send client-credentials.
     *
     * @return headers with Authorization header added
     */
    private HttpHeaders getHeadersWithClientCredentials() {
        String plainClientCredentials = "trusted-client:trusted-client-secret";
        String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));

        HttpHeaders headers = getHeaders();
        headers.add("Authorization", "Basic " + base64ClientCredentials);
        return headers;
    }

    /**
     * Send a POST request [on /oauth/token] to get an access_token, which will then be send with each request.
     *
     * @return access token for each request
     */
    @SuppressWarnings("unchecked")
    private AuthTokenInfo sendTokenRequest() {

        HttpEntity<String> request = new HttpEntity<>(getHeadersWithClientCredentials());
        ResponseEntity<Object> response =
                restTemplate.exchange(AUTH_SERVER + PASSWORD_GRANT + USERNAME + usernameValue + PASSWORD + passwordValue,
                        HttpMethod.POST, request, Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
        AuthTokenInfo tokenInfo = null;

        if (map != null) {
            tokenInfo = new AuthTokenInfo();
            tokenInfo.setAccessToken((String) map.get("access_token"));
            tokenInfo.setTokenType((String) map.get("token_type"));
            tokenInfo.setRefreshToken((String) map.get("refresh_token"));
            tokenInfo.setExpiresIn((int) map.get("expires_in"));
            tokenInfo.setScope((String) map.get("scope"));
        } else {
            LOGGER.debug("User does not exist");
        }
        return tokenInfo;
    }

    /**
     * Get customer by ID
     *
     * @param customerId ID of the customer to be found
     * @return customer entity
     */
    public ResponseEntity<Customer> getCustomerByNumber(Long customerId) {
        AuthTokenInfo tokenInfo = sendTokenRequest();
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        ResponseEntity<Customer> customerResponseEntity =
                restTemplate.exchange(REST_RESOURCE_URI + "/customers/" + customerId + ACCESS_TOKEN + tokenInfo.getAccessToken(),
                        HttpMethod.GET, request, Customer.class);
        System.out.println(customerResponseEntity.getBody());
        return customerResponseEntity;
    }
}
