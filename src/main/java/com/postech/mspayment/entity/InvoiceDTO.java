package com.postech.mspayment.entity;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    @NotNull
    private Long customerId;

    @NotNull
    private Long basketId;

    @NotNull
    private List<ProductItem> products;

    @NotNull
    private BigDecimal totalAmount;

    private Boolean status;

    public InvoiceDTO(long l, Long customerId, Long basketId, BigDecimal amount) {
    }

    public Invoice toInvoice() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(this.customerId);
        invoice.setBasketId(this.basketId);
        invoice.setProducts(this.products);
        invoice.setTotalAmount(this.totalAmount);
        invoice.setStatus(this.status);
        return invoice;
    }

    public Invoice fromInvoice(Invoice invoice) {
        Invoice invoiceDTO = new Invoice();
        invoiceDTO.setCustomerId(invoice.getCustomerId());
        invoiceDTO.setBasketId(invoice.getBasketId());
        invoiceDTO.setProducts(invoice.getProducts());
        invoiceDTO.setTotalAmount(invoice.getTotalAmount());
        invoiceDTO.setStatus(invoice.getStatus());
        return invoiceDTO;
    }
}
