package com.postech.mspayment.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name="invoice")
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;  // Referencia ao ID do cliente

    private String customerName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "invoice", orphanRemoval = true)
    @JsonManagedReference
    private List<ProductItem> products;

    private BigDecimal totalAmount; // La valeur de la somme de tous les produits

    private BigDecimal discount; // Si tem um desconto entao a gente diminui e poe em totalPayment somente o valor pago

    private BigDecimal totalPayment;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean status; // A gente poe a invoice em pending se o pagamento ainda nao foi efetuado

    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public void setDeleted() {
        this.deletedAt = LocalDateTime.now();
    }

    public InvoiceDTO toInvoiceDTO() {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setCustomerId(this.customerId);
        invoiceDTO.setCustomerName(this.customerName);
        invoiceDTO.setProducts(this.products);
        invoiceDTO.setTotalAmount(this.totalAmount);
        invoiceDTO.setDiscount(this.discount);
        invoiceDTO.setTotalPayment(this.totalPayment);
        invoiceDTO.setStatus(this.status);
        return invoiceDTO;
    }

    public Invoice fromDTO(InvoiceDTO invoiceDTO) {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(invoiceDTO.getCustomerId());
        invoice.setCustomerName(invoiceDTO.getCustomerName());
        invoice.setProducts(invoiceDTO.getProducts());
        invoice.setTotalAmount(invoiceDTO.getTotalAmount());
        invoice.setDiscount(invoiceDTO.getDiscount());
        invoice.setTotalPayment(invoiceDTO.getTotalPayment());
        invoice.setStatus(invoiceDTO.getStatus());
        return invoice;
    }

}
