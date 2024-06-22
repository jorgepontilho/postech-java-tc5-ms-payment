package com.postech.mspayment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class CustomerService {

    @Autowired
    private RestTemplate restTemplate;

    private final String USER_SERVICE_URL = "http://localhost:8083/customer";

    public boolean customerExists(Long customerId) {
        ResponseEntity<Boolean> response = restTemplate.getForEntity(USER_SERVICE_URL + "/{id}/exists", Boolean.class, customerId);
        return Boolean.TRUE.equals(response.getBody());
    }
}
