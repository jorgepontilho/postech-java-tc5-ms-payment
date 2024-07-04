package com.postech.mspayment.controller;

import com.postech.mspayment.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    public void testInitiatePayment() throws Exception {
        String paymentRequestJson = "{ \"customerId\": 1, \"basketId\": 1, \"amount\": 100.00 }";

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(paymentRequestJson))
                        .andExpect(status().isOk())
                        .andExpect(content().string("Payment processed successfully"));
    }

    @Test
    public void testInitiatePaymentException() throws Exception {
        String paymentRequestJson = "{ \"customerId\": 1, \"basketId\": 1, \"amount\": 100.00 }";

        // Mocking the paymentService to throw an exception
        Mockito.doThrow(new RuntimeException("Payment failed")).when(paymentService)
                .processPayment(Mockito.anyLong(), Mockito.anyLong(), Mockito.any());

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(paymentRequestJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Payment failed"));
    }
}
