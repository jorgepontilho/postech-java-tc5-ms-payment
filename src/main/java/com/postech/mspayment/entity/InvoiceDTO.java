package com.postech.mspayment.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    @NotNull
    private Long customerId;

    @NotNull
    private Long cartId;

    @NotNull
    private BigDecimal totalAmount;

    private Boolean status;

    public InvoiceDTO(long l, Long customerId, Long cartId, BigDecimal amount) {
    }

    public Invoice toInvoice() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(this.customerId);
        invoice.setCartId(this.cartId);
        invoice.setTotalAmount(this.totalAmount);
        invoice.setStatus(this.status);
        return invoice;
    }

    public Invoice fromInvoice(Invoice invoice) {
        Invoice invoiceDTO = new Invoice();
        invoiceDTO.setCustomerId(invoice.getCustomerId());
        invoiceDTO.setCartId(invoice.getCartId());
        invoiceDTO.setTotalAmount(invoice.getTotalAmount());
        invoiceDTO.setStatus(invoice.getStatus());
        return invoiceDTO;
    }
}
