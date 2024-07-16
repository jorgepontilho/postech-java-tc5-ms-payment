package com.postech.mspayment.usecase;

import com.postech.mspayment.entity.Invoice;
import com.postech.mspayment.entity.InvoiceDTO;
import com.postech.mspayment.repository.InvoiceRepository;
import com.postech.mspayment.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class CreateInvoiceUseCase {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private InvoiceRepository invoiceRepository;

    public InvoiceDTO execute(Long customerId, Long cartId, BigDecimal amount) throws Exception {

        logger.info(" * Creating invoice use case ");

        // Cria a Invoice
        Invoice invoice = new Invoice();
        invoice.setCustomerId(customerId);
        invoice.setCartId(cartId);
        invoice.setTotalAmount(amount);
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setUpdatedAt(LocalDateTime.now());
        Invoice invoiceSaved = invoiceRepository.save(invoice);

        return invoiceSaved.toInvoiceDTO();
    }
}

