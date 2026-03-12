package org.mt.ms_orders.infraestructure.entrypoints.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreatedOrderDto {
    private int id;
    private int idTable;
    private String customerName;
    private BigDecimal totalCost;
    private String notes;
    private LocalDateTime createdAt;
    private List<CreatedOrderProduct> products;
}
