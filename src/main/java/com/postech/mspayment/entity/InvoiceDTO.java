package com.postech.mspayment.entity;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
public class InvoiceDTO {

    @NotNull
    private Long customerId;

    @NotNull
    private String customerName;

    @NotNull
    private List<ProductItem> products;

    @NotNull
    private BigDecimal totalAmount;

    @Nullable
    private BigDecimal discount;
    @NotNull
    private BigDecimal totalPayment;
    private Boolean status;

}
