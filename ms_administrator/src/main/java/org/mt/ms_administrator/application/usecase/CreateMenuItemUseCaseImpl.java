package org.mt.ms_administrator.application.usecase;

import lombok.RequiredArgsConstructor;
import org.mt.ms_administrator.domain.model.AuditLog;
import org.mt.ms_administrator.domain.model.MenuItem;
import org.mt.ms_administrator.domain.ports.in.CreateMenuItemCommand;
import org.mt.ms_administrator.domain.ports.in.CreateMenuItemUseCase;
import org.mt.ms_administrator.domain.ports.out.AuditLogRepository;
import org.mt.ms_administrator.domain.ports.out.MenuItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateMenuItemUseCaseImpl implements CreateMenuItemUseCase {

    private final MenuItemRepository menuItemRepository;
    private final AuditLogRepository auditLogRepository;

    @Override
    @Transactional
    public Mono<MenuItem> execute(CreateMenuItemCommand command) {
        return menuItemRepository.save(command.toMenuItem())
                .flatMap(saved ->
                        auditLogRepository.save(createAuditLog(saved, command.getUserId()))
                                .thenReturn(saved)
                );
    }

    private AuditLog createAuditLog(MenuItem item, String userId) {
        return AuditLog.builder()
                .schemaName("restaurante")
                .tableName("menu_items")
                .action("INSERT")
                .userId(userId)
                .newValues(item.toString()) // O JSON con mapper
                .createdAt(java.time.LocalDateTime.now())
                .build();
    }
}