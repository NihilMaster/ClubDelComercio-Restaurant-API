package org.mt.ms_administrator.infrastructure.input.mapper;

import org.mt.ms_administrator.domain.model.MenuItem;
import org.mt.ms_administrator.domain.ports.in.CreateMenuItemCommand;
import org.mt.ms_administrator.domain.ports.in.UpdateMenuItemCommand;
import org.mt.ms_administrator.infrastructure.input.dto.request.CreateMenuItemRequest;
import org.mt.ms_administrator.infrastructure.input.dto.request.UpdateMenuItemRequest;
import org.mt.ms_administrator.infrastructure.input.dto.response.MenuItemResponse;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {

    public CreateMenuItemCommand toCommand(CreateMenuItemRequest request, String userId) {
        return CreateMenuItemCommand.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .classificationId(request.getClassificationId())
                .available(request.getAvailable())
                .userId(userId)
                .build();
    }

    public UpdateMenuItemCommand toCommand(Long id, UpdateMenuItemRequest request, String userId) {
        return UpdateMenuItemCommand.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .classificationId(request.getClassificationId())
                .available(request.getAvailable())
                .userId(userId)
                .build();
    }

    public MenuItemResponse toResponse(MenuItem item) {
        return MenuItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice())
                .classificationId(item.getClassificationId())
                .available(item.getAvailable())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }
}