package com.postech.mspayment.entity;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceDTO {

    @NotNull
    private Long customerId;

    @NotNull
    private String customerName;

    @NotNull
    private List<Invoice.ProductItem> products;

    @NotNull
    private BigDecimal totalAmount;

    @Nullable
    private Double discount;
    @NotNull
    private BigDecimal totalPayment;
    @NotNull
    private Boolean status;

}
