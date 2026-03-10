package org.mt.ms_administrator.infrastructure.input.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long classificationId;
    private String classificationName;
    private Boolean available;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}