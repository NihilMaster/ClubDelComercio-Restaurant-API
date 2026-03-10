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
@Table(value = "audit_logs", schema = "transaccional")
public class AuditLogEntity {

    @Id
    private Long id;

    @Column("schema_name")
    private String schemaName;

    @Column("table_name")
    private String tableName;

    private String action;

    @Column("user_id")
    private String userId;

    @Column("old_values")
    private String oldValues;

    @Column("new_values")
    private String newValues;

    @Column("created_at")
    private LocalDateTime createdAt;
}