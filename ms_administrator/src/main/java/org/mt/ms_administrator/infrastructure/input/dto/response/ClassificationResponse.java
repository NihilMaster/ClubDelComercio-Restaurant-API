package org.mt.ms_administrator.infrastructure.input.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassificationResponse {

    private Long id;
    private String name;
    private String description;
    private Integer sortOrder;
    private LocalDateTime createdAt;
}