package org.mt.ms_administrator.application.usecase;

import lombok.RequiredArgsConstructor;
import org.mt.ms_administrator.domain.model.MenuItem;
import org.mt.ms_administrator.domain.ports.in.GetMenuItemUseCase;
import org.mt.ms_administrator.domain.ports.out.MenuItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetMenuItemUseCaseImpl implements GetMenuItemUseCase {

    private final MenuItemRepository menuItemRepository;

    @Override
    public Mono<MenuItem> execute(Long id) {
        return menuItemRepository.findById(id);
    }
}