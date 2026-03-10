package org.mt.ms_administrator.infrastructure.output.repository;

import lombok.RequiredArgsConstructor;
import org.mt.ms_administrator.domain.model.MenuItem;
import org.mt.ms_administrator.domain.ports.out.MenuItemRepository;
import org.mt.ms_administrator.infrastructure.output.entity.MenuItemEntity;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class R2dbcMenuItemRepository implements MenuItemRepository {

    private final R2dbcEntityTemplate template;

    @Override
    public Mono<MenuItem> save(MenuItem item) {
        MenuItemEntity entity = toEntity(item);
        return template.insert(entity).map(this::toDomain);
    }

    @Override
    public Mono<MenuItem> findById(Long id) {
        return template.select(MenuItemEntity.class)
                .matching(Query.query(Criteria.where("id").is(id)))
                .one()
                .map(this::toDomain);
    }

    @Override
    public Flux<MenuItem> findAll() {
        return template.select(MenuItemEntity.class)
                .all()
                .map(this::toDomain);
    }

    @Override
    public Mono<MenuItem> update(MenuItem item) {
        MenuItemEntity entity = toEntity(item);
        return template.update(entity).map(this::toDomain);
    }

    @Override
    public Mono<Void> delete(Long id) {
        /* return template.delete(MenuItemEntity.class)
                .matching(Query.query(Criteria.where("id").is(id)))
                .then(); */
        return Mono.empty(); // Temporal
    }

    @Override
    public Flux<MenuItem> findByClassificationId(Long classificationId) {
        return template.select(MenuItemEntity.class)
                .matching(Query.query(Criteria.where("classification_id").is(classificationId)))
                .all()
                .map(this::toDomain);
    }

    // ← Mappers Entity ↔ Domain
    private MenuItemEntity toEntity(MenuItem domain) {
        return MenuItemEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .price(domain.getPrice())
                .classificationId(domain.getClassificationId())
                .available(domain.getAvailable())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    private MenuItem toDomain(MenuItemEntity entity) {
        return MenuItem.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .classificationId(entity.getClassificationId())
                .available(entity.getAvailable())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}