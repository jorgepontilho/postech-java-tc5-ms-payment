package com.postech.mspayment.service;

import com.postech.mspayment.entity.Invoice;
import com.postech.mspayment.entity.InvoiceDTO;
import com.postech.mspayment.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private com.postech.mspayment.usecase.CreateInvoiceUseCase createInvoiceUseCase;

    public Invoice createInvoice(InvoiceDTO invoiceDTO) {
        return createInvoiceUseCase.execute(invoiceDTO);
    }

    //Check si l'invoice existe et qu'elle n'est pas deleted sinon ne la retourn pas
    public Optional<Invoice> getInvoiceById(Long id) {

        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);

        if (optionalInvoice.isPresent() && optionalInvoice.get().getDeletedAt() == null) {
            return Optional.empty();
        } else {
            return optionalInvoice;
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
