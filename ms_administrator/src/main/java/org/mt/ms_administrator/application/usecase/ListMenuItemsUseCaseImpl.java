package org.mt.ms_administrator.application.usecase;

import lombok.RequiredArgsConstructor;
import org.mt.ms_administrator.domain.model.MenuItem;
import org.mt.ms_administrator.domain.ports.in.ListMenuItemsUseCase;
import org.mt.ms_administrator.domain.ports.out.MenuItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ListMenuItemsUseCaseImpl implements ListMenuItemsUseCase {

    private final MenuItemRepository menuItemRepository;

    @Override
    public Flux<MenuItem> execute() {
        return menuItemRepository.findAll();
    }
}