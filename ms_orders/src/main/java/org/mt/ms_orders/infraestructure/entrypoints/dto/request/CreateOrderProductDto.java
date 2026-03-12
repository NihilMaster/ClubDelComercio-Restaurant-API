package org.mt.ms_orders.infraestructure.entrypoints.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderProductDto {
    @NotNull(message = "Product ID is required")
    private Integer productId;
    @NotNull(message = "Product quantity is required")
    private Integer quantity;
}
