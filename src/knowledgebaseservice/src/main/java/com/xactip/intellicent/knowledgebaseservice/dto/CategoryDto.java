package com.xactip.intellicent.knowledgebaseservice.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryDto(
        String id,
        @NotBlank(message = "title field must not be empty.")
        String title,
        String description
) {
}
