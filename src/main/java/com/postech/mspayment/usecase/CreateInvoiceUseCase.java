package com.postech.mspayment.usecase;

import com.postech.mspayment.entity.Invoice;
import com.postech.mspayment.entity.InvoiceDTO;
import com.postech.mspayment.repository.InvoiceRepository;
import com.postech.mspayment.service.CustomerService;
import com.postech.mspayment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CreateInvoiceUseCase {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    public Invoice execute(InvoiceDTO invoiceDTO) {
        // Verifica se o cliente existe
        if (!customerService.customerExists(invoiceDTO.getCustomerId())) {
            throw new RuntimeException("Customer not found with id " + invoiceDTO.getCustomerId());
        }

        // Verifica se os produtos existem e têm estoque suficiente
        invoiceDTO.getProducts().forEach(product -> {
            if (!productService.productExistsAndAvailable(product.productId, product.quantity)) { //TODO corrigir
                throw new RuntimeException("Product not found or unavailable with id " + product.productId);
            }
        });

        // Cria a Invoice
        Invoice invoice = new Invoice();
        invoice.setCustomerId(invoiceDTO.getCustomerId());
        invoice.setProducts(invoiceDTO.getProducts());
        invoice.setTotalAmount(invoiceDTO.getTotalAmount());
        invoice.setDiscount(invoiceDTO.getDiscount());
        invoice.setTotalPayment(invoiceDTO.getTotalPayment());
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setUpdatedAt(LocalDateTime.now());

        return invoiceRepository.save(invoice);
    }
}

