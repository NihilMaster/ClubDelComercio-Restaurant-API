package org.mt.ms_administrator.application.usecase;

import lombok.RequiredArgsConstructor;
import org.mt.ms_administrator.domain.model.AuditLog;
import org.mt.ms_administrator.domain.model.MenuItem;
import org.mt.ms_administrator.domain.ports.in.DeleteMenuItemUseCase;
import org.mt.ms_administrator.domain.ports.out.AuditLogRepository;
import org.mt.ms_administrator.domain.ports.out.MenuItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DeleteMenuItemUseCaseImpl implements DeleteMenuItemUseCase {

    private final MenuItemRepository menuItemRepository;
    private final AuditLogRepository auditLogRepository;

    @Override
    @Transactional
    public Mono<Void> execute(Long id) {
        return menuItemRepository.findById(id)
                .flatMap(item ->
                        auditLogRepository.save(createAuditLog(item))
                                .then(menuItemRepository.delete(id))
                );
    }

    private AuditLog createAuditLog(MenuItem item) {
        return AuditLog.builder()
                .schemaName("restaurante")
                .tableName("menu_items")
                .action("DELETE")
                .oldValues(item.toString())
                .createdAt(java.time.LocalDateTime.now())
                .build();
    }
}