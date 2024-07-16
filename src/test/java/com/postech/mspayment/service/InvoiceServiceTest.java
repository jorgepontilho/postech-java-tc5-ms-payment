package com.postech.mspayment.service;

import com.postech.mspayment.entity.Invoice;
import com.postech.mspayment.entity.InvoiceDTO;
import com.postech.mspayment.repository.InvoiceRepository;
import com.postech.mspayment.usecase.CreateInvoiceUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private CreateInvoiceUseCase createInvoiceUseCase;

    @InjectMocks
    private InvoiceService invoiceService;

    private Invoice invoice;

    @BeforeEach
    void setUp() {
        invoice = new Invoice();
        invoice.setId(1L);
        invoice.setCustomerId(1L);
        invoice.setCartId(1L); //basket id
        invoice.setTotalAmount(BigDecimal.valueOf(100.00));
        invoice.setDeletedAt(null);
    }

    @Test
    void testCreateInvoice() throws Exception {
        InvoiceDTO invoiceDTO = new InvoiceDTO(1L, 1L, BigDecimal.valueOf(100.00), null);
        when(createInvoiceUseCase.execute(anyLong(), anyLong(), any(BigDecimal.class))).thenReturn(invoiceDTO);

        InvoiceDTO createdInvoice = invoiceService.createInvoice(1L, 1L, BigDecimal.valueOf(100.00));
        assertNotNull(createdInvoice);
        assertEquals(BigDecimal.valueOf(100.00), createdInvoice.getTotalAmount());
    }

    @Test
    void testGetInvoiceById() {
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));

        Optional<Invoice> foundInvoice = invoiceService.getInvoiceById(1L);
        assertTrue(foundInvoice.isPresent());
        assertEquals(1L, foundInvoice.get().getId());
    }

    @Test
    void testGetAllInvoices() {
        List<Invoice> invoices = Arrays.asList(invoice);
        when(invoiceRepository.findAll()).thenReturn(invoices);

        List<Invoice> allInvoices = invoiceService.getAllInvoices();
        assertFalse(allInvoices.isEmpty());
        assertEquals(1, allInvoices.size());
        assertEquals(1L, allInvoices.get(0).getId());
    }

    @Test
    void testDeleteInvoice() {
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));

        invoiceService.deleteInvoice(1L);
        verify(invoiceRepository, times(1)).save(invoice);
        assertNotNull(invoice.getDeletedAt());
    }
}
