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

    @Email
    @Nullable
    private String email;

    @NotNull
    private String customerName;

    @NotNull
    private List<ProductItem> products;

    @NotNull
    private BigDecimal totalAmount;

    private Boolean status;

}
