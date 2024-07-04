package com.postech.mspayment.service;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);


    @Autowired
    private RestTemplate restTemplate;

    public void processPayment(Long customerId, Long basketId, BigDecimal amount) throws Exception {

        try {
            // Aqui o processo de pagamento sera feito pela empresa Stripe e quando acabar e obtiver sucesso ele
            // faz uma chamada para o meu endpoint de criacao de invoice de maneira asyncrona sem webhook
            // Como nao implementei o serviço de verdade, criei um método que fara a chamada assíncrona

            //Se success ele chama a criação de invoice, senao ele joga a aexcecao para o frontend
            logger.info(" * Creating payment for customerId: {}, basketId: {}, amount: {}", customerId, basketId, amount);

            initiateInvoiceCreation(customerId, basketId, amount);

        } catch (Exception e) {
            throw new Exception("Error creating payment", e); //Seria uma StripeException a ser tratada no frontend
        }
    }

    @Async
    public void initiateInvoiceCreation(Long customerId, Long basketId, BigDecimal amount) {
        try {
            String formattedAmount = amount.toPlainString();
            // Chama o endpoint de criação de invoice
            String url = "http://localhost:8083/api/invoices/create/" + customerId + "/" + basketId + "/" + formattedAmount;
            restTemplate.getForEntity(url, String.class);
        } catch (Exception e) {
            System.err.println("Error creating invoice: " + e.getMessage());
        }
    }
}
