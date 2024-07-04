package com.postech.mspayment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class CustomerService {

    @Autowired
    private RestTemplate restTemplate;

    private final String USER_SERVICE_URL = "http://localhost:8080/api/users";

    public UserDTO getCustomerById(Long customerId) throws Exception {
        //String url = USER_SERVICE_URL + "/" + customerId;
        //        try {
        //            ResponseEntity<UserDTO> response = restTemplate.getForEntity(url, UserDTO.class);
        //            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
        //                return response.getBody();
        //            } else {
        //                throw new Exception("Error retrieving customer data");
        //            }
        //        } catch (Exception e) {
        //            throw new Exception("Error retrieving customer data");
        //        }
        return new UserDTO(customerId, "exemple@email.com", "customer1");
    }

    public static class UserDTO {
        private Long id;
        private String email;
        private String name;

        public UserDTO(Long id, String email, String name) {
            this.id = id;
            this.email = email;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
