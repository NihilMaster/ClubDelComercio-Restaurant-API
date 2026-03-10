package org.mt.ms_administrator.infrastructure.output.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "menu_items", schema = "restaurante")
public class MenuItemEntity {

    @Id
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;

    @Column("classification_id")
    private Long classificationId;

    private Boolean available;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}