package org.mt.ms_orders.infraestructure.entrypoints.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatedOrderProduct {
    private int id;
    private int productId;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subPrice;
}
