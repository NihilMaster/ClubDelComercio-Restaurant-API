package org.mt.ms_administrator.infrastructure.output.repository;

import lombok.RequiredArgsConstructor;
import org.mt.ms_administrator.domain.model.AuditLog;
import org.mt.ms_administrator.domain.ports.out.AuditLogRepository;
import org.mt.ms_administrator.infrastructure.output.entity.AuditLogEntity;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class R2dbcAuditLogRepository implements AuditLogRepository {

    private final R2dbcEntityTemplate template;

    @Override
    public Mono<AuditLog> save(AuditLog log) {
        AuditLogEntity entity = toEntity(log);
        return template.insert(entity).map(this::toDomain);
    }

    private AuditLogEntity toEntity(AuditLog domain) {
        return AuditLogEntity.builder()
                .schemaName(domain.getSchemaName())
                .tableName(domain.getTableName())
                .action(domain.getAction())
                .userId(domain.getUserId())
                .oldValues(domain.getOldValues())
                .newValues(domain.getNewValues())
                .createdAt(domain.getCreatedAt())
                .build();
    }

    private AuditLog toDomain(AuditLogEntity entity) {
        return AuditLog.builder()
                .id(entity.getId())
                .schemaName(entity.getSchemaName())
                .tableName(entity.getTableName())
                .action(entity.getAction())
                .userId(entity.getUserId())
                .oldValues(entity.getOldValues())
                .newValues(entity.getNewValues())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}