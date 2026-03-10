package org.mt.ms_administrator.infrastructure.input.controller;

import lombok.RequiredArgsConstructor;
import org.mt.ms_administrator.domain.model.MenuClassification;
import org.mt.ms_administrator.domain.ports.in.*;
import org.mt.ms_administrator.infrastructure.input.dto.request.CreateClassificationRequest;
import org.mt.ms_administrator.infrastructure.input.dto.response.ClassificationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/classifications")
@RequiredArgsConstructor
public class MenuClassificationController {

    private final CreateClassificationUseCase createUseCase;
    private final GetClassificationUseCase getUseCase;
    private final ListClassificationsUseCase listUseCase;
    private final UpdateClassificationUseCase updateUseCase;
    // private final DeleteClassificationUseCase deleteUseCase; // ← Temporalmente comentado

    @PostMapping
    public Mono<ResponseEntity<ClassificationResponse>> create(@RequestBody CreateClassificationRequest request) {
        return createUseCase.execute(request.toCommand())
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping
    public Flux<ClassificationResponse> list() {
        return listUseCase.execute().map(this::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ClassificationResponse>> getById(@PathVariable Long id) {
        return getUseCase.execute(id)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ClassificationResponse>> update(
            @PathVariable Long id,
            @RequestBody CreateClassificationRequest request) {

        var command = request.toCommand();
        var updateCommand = UpdateClassificationCommand.builder()
                .id(id)
                .name(command.getName())
                .description(command.getDescription())
                .sortOrder(command.getSortOrder())
                .userId(command.getUserId())
                .build();

        return updateUseCase.execute(updateCommand)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // @DeleteMapping("/{id}")  // ← Temporalmente comentado
    // public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
    //     return deleteUseCase.execute(id)
    //         .then(Mono.just(ResponseEntity.noContent().<Void>build()))
    //         .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    // }

    private ClassificationResponse toResponse(MenuClassification c) {
        return ClassificationResponse.builder()
                .id(c.getId())
                .name(c.getName())
                .description(c.getDescription())
                .sortOrder(c.getSortOrder())
                .createdAt(c.getCreatedAt())
                .build();
    }
}