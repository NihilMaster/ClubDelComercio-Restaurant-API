package org.mt.ms_administrator.infrastructure.output.repository;

import lombok.RequiredArgsConstructor;
import org.mt.ms_administrator.domain.model.MenuClassification;
import org.mt.ms_administrator.domain.ports.out.MenuClassificationRepository;
import org.mt.ms_administrator.infrastructure.output.entity.MenuClassificationEntity;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class R2dbcMenuClassificationRepository implements MenuClassificationRepository {

    private final R2dbcEntityTemplate template;

    @Override
    public Mono<MenuClassification> save(MenuClassification classification) {
        MenuClassificationEntity entity = toEntity(classification);
        return template.insert(entity).map(this::toDomain);
    }

    @Override
    public Mono<MenuClassification> findById(Long id) {
        return template.select(MenuClassificationEntity.class)
                .matching(Query.query(Criteria.where("id").is(id)))
                .one()
                .map(this::toDomain);
    }

    @Override
    public Flux<MenuClassification> findAll() {
        return template.select(MenuClassificationEntity.class)
                .all()
                .map(this::toDomain);
    }

    @Override
    public Mono<MenuClassification> update(MenuClassification classification) {
        MenuClassificationEntity entity = toEntity(classification);
        return template.update(entity).map(this::toDomain);
    }

    @Override
    public Mono<Void> delete(Long id) {
        /* return template.delete(MenuClassificationEntity.class)
                .matching(Query.query(Criteria.where("id").is(id)))
                .then(); */
        return Mono.empty();  // ← Retorna vacío temporalmente
    }

    private MenuClassificationEntity toEntity(MenuClassification domain) {
        return MenuClassificationEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .sortOrder(domain.getSortOrder())
                .createdAt(domain.getCreatedAt())
                .build();
    }

    private MenuClassification toDomain(MenuClassificationEntity entity) {
        return MenuClassification.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .sortOrder(entity.getSortOrder())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}