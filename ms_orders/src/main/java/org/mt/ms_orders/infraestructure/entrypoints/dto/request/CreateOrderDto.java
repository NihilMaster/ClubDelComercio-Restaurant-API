package org.mt.ms_orders.infraestructure.entrypoints.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDto {
    @NotBlank(message = "Customer name is required")
    private String customerName;
    private String notes;
    @NotBlank(message = "At least one product is required")
    private List<CreateOrderProductDto> products;
}
