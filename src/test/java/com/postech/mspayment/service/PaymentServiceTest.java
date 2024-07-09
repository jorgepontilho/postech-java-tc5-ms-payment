package com.postech.mspayment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PaymentServiceTest {

    @Mock
    private RestTemplate restTemplate;

    //pra verificar a chamada do método initiateInvoiceCreation() dentro da mesma instância de PaymentService
    @Spy
    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessPayment() throws Exception {
        Long customerId = 1L;
        Long basketId = 1L;
        BigDecimal amount = new BigDecimal("10.00");

        // Chamar o método a ser testado
        paymentService.processPayment(customerId, basketId, amount);

        // Verificar se o método initiateInvoiceCreation foi chamado
        verify(paymentService, times(1)).initiateInvoiceCreation(customerId, basketId, amount);
    }

    @Test
    void testInitiateInvoiceCreation() {
        Long customerId = 1L;
        Long basketId = 1L;
        BigDecimal amount = new BigDecimal("10.00");

        // Chama o método pra ser testado
        paymentService.initiateInvoiceCreation(customerId, basketId, amount);

        String expectedUrl = "http://localhost:8083/api/invoices/create/" + customerId + "/" + basketId + "/" + amount.toPlainString();

        // Verificar se o RestTemplate foi chamado com a URL correta
        verify(restTemplate, times(1)).getForEntity(expectedUrl, String.class);
    }

}
