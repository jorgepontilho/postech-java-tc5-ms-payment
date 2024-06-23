package com.postech.mspayment.service;

import com.postech.mspayment.entity.Invoice;
import com.postech.mspayment.entity.InvoiceDTO;
import com.postech.mspayment.entity.ProductItem;
import com.postech.mspayment.repository.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private com.postech.mspayment.usecase.CreateInvoiceUseCase createInvoiceUseCase;

    public Invoice createInvoice(InvoiceDTO invoiceDTO) {

        try {
            Invoice invoice = new Invoice();
            invoice.setCustomerId(invoiceDTO.getCustomerId());
            invoice.setCustomerName(invoiceDTO.getCustomerName());
            invoice.setTotalAmount(invoiceDTO.getTotalAmount());
            invoice.setDiscount(invoiceDTO.getDiscount());
            invoice.setTotalPayment(invoiceDTO.getTotalPayment());
            invoice.setStatus(invoiceDTO.getStatus());

            // Log antes de salvar a Invoice
            logger.info("Saving Invoice: {}", invoice);

            Invoice savedInvoice = invoiceRepository.save(invoice);

            // Log depois de salvar a Invoice e antes de associar ProductItem
            logger.info("Invoice saved with ID: {}", savedInvoice.getId());

            // Associar produtos à invoice
            List<ProductItem> products = invoiceDTO.getProducts();
            for (ProductItem product : products) {
                product.setInvoice(savedInvoice);
            }
            savedInvoice.setProducts(products);

            // Salvar novamente a invoice com os produtos associados
            return invoiceRepository.save(savedInvoice);
        } catch (Exception e) {
            logger.error("Error saving invoice", e);
            throw e;
        }
    }

    public Invoice createInvoice2(InvoiceDTO invoiceDTO) {
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
