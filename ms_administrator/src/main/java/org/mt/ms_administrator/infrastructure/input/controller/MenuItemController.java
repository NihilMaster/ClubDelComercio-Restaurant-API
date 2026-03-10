package org.mt.ms_administrator.infrastructure.input.controller;

import lombok.RequiredArgsConstructor;
import org.mt.ms_administrator.domain.ports.in.*;
import org.mt.ms_administrator.infrastructure.input.dto.request.CreateMenuItemRequest;
import org.mt.ms_administrator.infrastructure.input.dto.request.UpdateMenuItemRequest;
import org.mt.ms_administrator.infrastructure.input.dto.response.MenuItemResponse;
import org.mt.ms_administrator.infrastructure.input.mapper.MenuItemMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private final CreateMenuItemUseCase createUseCase;
    private final UpdateMenuItemUseCase updateUseCase;
    private final DeleteMenuItemUseCase deleteUseCase;
    private final GetMenuItemUseCase getUseCase;
    private final ListMenuItemsUseCase listUseCase;
    private final MenuItemMapper mapper;

    @PostMapping
    public Mono<ResponseEntity<MenuItemResponse>> create(
            @RequestBody CreateMenuItemRequest request,
            @RequestHeader(value = "X-User-Id", required = false) String userId) {

        return createUseCase.execute(mapper.toCommand(request, userId != null ? userId : "system"))
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping
    public Flux<MenuItemResponse> list() {
        return listUseCase.execute().map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<MenuItemResponse>> getById(@PathVariable Long id) {
        return getUseCase.execute(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<MenuItemResponse>> update(
            @PathVariable Long id,
            @RequestBody UpdateMenuItemRequest request,
            @RequestHeader(value = "X-User-Id", required = false) String userId) {

        return updateUseCase.execute(mapper.toCommand(id, request, userId != null ? userId : "system"))
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return deleteUseCase.execute(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }
}