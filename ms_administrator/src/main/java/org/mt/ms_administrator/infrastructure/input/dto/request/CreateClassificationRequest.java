package org.mt.ms_administrator.infrastructure.input.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.mt.ms_administrator.domain.ports.in.CreateClassificationCommand;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateClassificationRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String name;

    private String description;

    private Integer sortOrder;

    public CreateClassificationCommand toCommand() {
        return CreateClassificationCommand.builder()
                .name(this.name)
                .description(this.description)
                .sortOrder(this.sortOrder)
                .userId("system")
                .build();
    }
}