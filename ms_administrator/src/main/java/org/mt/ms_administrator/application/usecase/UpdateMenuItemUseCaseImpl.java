package org.mt.ms_administrator.application.usecase;

import lombok.RequiredArgsConstructor;
import org.mt.ms_administrator.domain.model.AuditLog;
import org.mt.ms_administrator.domain.model.MenuItem;
import org.mt.ms_administrator.domain.ports.in.UpdateMenuItemCommand;
import org.mt.ms_administrator.domain.ports.in.UpdateMenuItemUseCase;
import org.mt.ms_administrator.domain.ports.out.AuditLogRepository;
import org.mt.ms_administrator.domain.ports.out.MenuItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateMenuItemUseCaseImpl implements UpdateMenuItemUseCase {

    private final MenuItemRepository menuItemRepository;
    private final AuditLogRepository auditLogRepository;

    @Override
    @Transactional
    public Mono<MenuItem> execute(UpdateMenuItemCommand command) {
        return menuItemRepository.findById(command.getId())
                .flatMap(existing ->
                        menuItemRepository.update(command.toMenuItem())
                                .flatMap(updated ->
                                        auditLogRepository.save(createAuditLog(existing, updated, command.getUserId()))
                                                .thenReturn(updated)
                                )
                );
    }

    private AuditLog createAuditLog(MenuItem oldItem, MenuItem newItem, String userId) {
        return AuditLog.builder()
                .schemaName("restaurante")
                .tableName("menu_items")
                .action("UPDATE")
                .userId(userId)
                .oldValues(oldItem.toString())
                .newValues(newItem.toString())
                .createdAt(java.time.LocalDateTime.now())
                .build();
    }
}