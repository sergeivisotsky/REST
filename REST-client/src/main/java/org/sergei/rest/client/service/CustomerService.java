package org.sergei.rest.client.service;

import org.sergei.rest.client.pojo.CustomerVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class CustomerService {

    private static final String REQUEST_URI_GET_BY_CUSTOMER_NUMBER = "http://localhost:8080/rest/api/v1/customers/{customerNumber}";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    @Autowired
    public CustomerService(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
    }


    public CustomerVO getCustomerByNumber(Long customerNumber) {

        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        CustomerVO customerVO = restTemplate.getForObject(REQUEST_URI_GET_BY_CUSTOMER_NUMBER, CustomerVO.class, customerNumber);

        logger.info(customerVO != null ? customerVO.toString() : null);

        return customerVO;
    }
}
