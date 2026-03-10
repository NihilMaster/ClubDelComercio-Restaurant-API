package org.mt.ms_administrator.infrastructure.input.mapper;

import org.mt.ms_administrator.domain.model.MenuClassification;
import org.mt.ms_administrator.infrastructure.input.dto.request.CreateClassificationRequest;
import org.mt.ms_administrator.infrastructure.input.dto.response.ClassificationResponse;
import org.springframework.stereotype.Component;

@Component
public class ClassificationMapper {

    public CreateClassificationRequest toRequest(MenuClassification classification) {
        return CreateClassificationRequest.builder()
                .name(classification.getName())
                .description(classification.getDescription())
                .sortOrder(classification.getSortOrder())
                .build();
    }

    public ClassificationResponse toResponse(MenuClassification classification) {
        return ClassificationResponse.builder()
                .id(classification.getId())
                .name(classification.getName())
                .description(classification.getDescription())
                .sortOrder(classification.getSortOrder())
                .createdAt(classification.getCreatedAt())
                .build();
    }
}