package org.mt.ms_administrator.infrastructure.output.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "menu_classifications", schema = "restaurante")
public class MenuClassificationEntity {

    @Id
    private Long id;
    private String name;
    private String description;
    private Integer sortOrder;

    @Column("created_at")
    private LocalDateTime createdAt;
}