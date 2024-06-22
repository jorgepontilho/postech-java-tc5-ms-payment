package com.postech.mspayment.entity;

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

    @ElementCollection
    private List<ProductItem> products;  // Lista de produtos

    private BigDecimal totalAmount; // La valeur de la somme de tous les produits

    private Double discount; // Si tem um desconto entao a gente diminui e poe em totalPayment somente o valor pago

    private BigDecimal totalPayment;

    private Boolean status; // A gente poe a invoice em pending se o pagamento ainda nao foi efetuado

    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public void setDeleted() {
        this.deletedAt = LocalDateTime.now();
    }

    @Embeddable
    public static class ProductItem {
        public Long productId;
        private String productName;
        public int quantity;
        private BigDecimal price;

        // Getters and Setters
    }
}
