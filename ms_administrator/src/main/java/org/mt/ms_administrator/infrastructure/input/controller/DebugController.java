package org.mt.ms_administrator.infrastructure.input.controller;

import lombok.RequiredArgsConstructor;
import org.mt.ms_administrator.domain.ports.out.MenuItemRepository;
import org.mt.ms_administrator.domain.ports.out.MenuClassificationRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Map;

@RestController
@RequestMapping("/debug")
@RequiredArgsConstructor
public class DebugController {

    private final MenuItemRepository menuItemRepository;
    private final MenuClassificationRepository classificationRepository;

    @GetMapping("/db")
    public Mono<Map<String, Object>> testConnection() {
        return Mono.zip(
                        menuItemRepository.findAll().collectList(),
                        classificationRepository.findAll().collectList()
                )
                .map(tuple -> Map.of(
                        "status", "OK",
                        "menuItemsCount", tuple.getT1().size(),
                        "classificationsCount", tuple.getT2().size()
                ));
    }
}