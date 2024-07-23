package com.postech.mspayment.service;

import com.postech.mspayment.entity.Invoice;
import com.postech.mspayment.entity.InvoiceDTO;
import com.postech.mspayment.repository.InvoiceRepository;
import com.postech.mspayment.usecase.CreateInvoiceUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CreateInvoiceUseCase createInvoiceUseCase;

    public InvoiceDTO createInvoice(Long customerId, Long cartId, BigDecimal amount) throws Exception {
        return createInvoiceUseCase.execute(customerId,cartId,amount);
    }

    //Check si l'invoice existe et qu'elle n'est pas deleted sinon ne la retourn pas
    public Optional<Invoice> getInvoiceById(Long id) {

        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);

        if (optionalInvoice.isPresent() && optionalInvoice.get().getDeletedAt() == null) {
            return optionalInvoice;
        } else {
            return Optional.empty();
        }
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public void deleteInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id " + id));
        invoice.setDeletedAt(LocalDateTime.now());
        invoiceRepository.save(invoice);
    }
}
