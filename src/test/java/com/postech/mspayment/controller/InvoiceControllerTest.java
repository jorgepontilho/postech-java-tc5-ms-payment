package com.postech.mspayment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.mspayment.entity.InvoiceDTO;
import com.postech.mspayment.service.InvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    @InjectMocks
    private InvoiceController invoiceController;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(invoiceController).build();
    }

    @Test
    void testCreateInvoice() throws Exception {
        Long customerId = 1L;
        Long basketId = 1L;
        BigDecimal amount = new BigDecimal("100.00");
        InvoiceDTO invoiceDTO = new InvoiceDTO(1L, customerId, basketId, amount);

        Mockito.when(invoiceService.createInvoice(customerId, basketId, amount)).thenReturn(invoiceDTO);

        mockMvc.perform(get("/api/invoices/create/{customerId}/{basketId}/{amount}", customerId, basketId, amount)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(invoiceDTO.getCustomerId()))
                .andExpect(jsonPath("$.basketId").value(invoiceDTO.getBasketId()))
                .andExpect(jsonPath("$.totalAmount").value(invoiceDTO.getTotalAmount()));
    }

    @Test
    void testCreateInvoiceException() throws Exception {
        Long customerId = 1L;
        Long basketId = 1L;
        BigDecimal amount = new BigDecimal("100.00");

        Mockito.when(invoiceService.createInvoice(customerId, basketId, amount)).thenThrow(new RuntimeException("Error creating invoice"));

        mockMvc.perform(get("/api/invoices/create/{customerId}/{basketId}/{amount}", customerId, basketId, amount)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error creating invoice"));
    }
}