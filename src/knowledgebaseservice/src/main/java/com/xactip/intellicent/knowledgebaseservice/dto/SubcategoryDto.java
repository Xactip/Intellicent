package com.xactip.intellicent.knowledgebaseservice.dto;

import jakarta.validation.constraints.NotBlank;

public record SubcategoryDto(
        String id,
        @NotBlank(message = "categoryId field must not be empty.")
        String categoryId,
        @NotBlank(message = "title field must not be empty.")
        String title,
        String description
) {
}
