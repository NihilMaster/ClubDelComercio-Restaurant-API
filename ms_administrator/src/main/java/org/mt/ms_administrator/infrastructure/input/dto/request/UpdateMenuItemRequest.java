package org.mt.ms_administrator.infrastructure.input.dto.request;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMenuItemRequest {

    private String name;
    private String description;
    private BigDecimal price;
    private Long classificationId;
    private Boolean available;
}