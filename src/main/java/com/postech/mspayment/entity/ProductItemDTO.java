package com.postech.mspayment.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductItemDTO {

    public String productName;

    public Long invoiceId;

    public int quantity;

    public BigDecimal price;

}
