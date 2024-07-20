package com.postech.mspayment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.mspayment.security.SecurityUser;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);


    @Autowired
    static RestTemplate restTemplate = new RestTemplate();

    private static String msUserUrl;

    @Value("${api.msuser.url}")
    public void setMsUserUrl(String msUserUrl) {
        this.msUserUrl = msUserUrl;
    }

    public static SecurityUser getUserFromToken(String token) {
        SecurityUser securityUser= new SecurityUser();
        try {
            String url = msUserUrl + "/" + token;
            ObjectMapper mapper = new ObjectMapper();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            Map<String, Object> map = mapper.readValue(response.getBody(), Map.class);
            securityUser.setLogin( map.get("login").toString());
            securityUser.setRole( map.get("role").toString());
            return securityUser;
        } catch (Exception e) {
            return null;
        }
    }

    public void processPayment(Long customerId, Long cartId, BigDecimal amount) throws Exception {

        try {
            // Aqui o processo de pagamento sera feito pela empresa Stripe e quando acabar e obtiver sucesso ele
            // faz uma chamada para o meu endpoint de criacao de invoice de maneira asyncrona sem webhook
            // Como nao implementei o serviço de verdade, criei um método que fara a chamada assíncrona

            //Se success ele chama a criação de invoice, senao ele joga a aexcecao para o frontend
            logger.info(" * Creating payment for customerId: {}, cartId: {}, amount: {}", customerId, cartId, amount);

            initiateInvoiceCreation(customerId, cartId, amount);

        } catch (Exception e) {
            throw new Exception("Error creating payment", e); //Seria uma StripeException a ser tratada no frontend
        }
    }

    @Async
    public void initiateInvoiceCreation(Long customerId, Long cartId, BigDecimal amount) {
        try {
            String formattedAmount = amount.toPlainString();
            // Chama o endpoint de criação de invoice
            String url = "http://localhost:8083/api/invoices/create/" + customerId + "/" + cartId + "/" + formattedAmount;
            restTemplate.getForEntity(url, String.class);
        } catch (Exception e) {
            System.err.println("Error creating invoice: " + e.getMessage());
        }
    }
}
