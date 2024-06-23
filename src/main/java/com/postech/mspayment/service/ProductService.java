package com.postech.mspayment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductService {

    @Autowired
    private RestTemplate restTemplate;

    private final String USER_SERVICE_URL = "http://localhost:8083/product";

    public boolean productExistsAndAvailable(Long productId, int quantity) {
        //ResponseEntity<Boolean> response = restTemplate.getForEntity(USER_SERVICE_URL + "/{id}/{quantity}/exists", Boolean.class, productId);
        //        return Boolean.TRUE.equals(response.getBody());
        return true;
    }

}
