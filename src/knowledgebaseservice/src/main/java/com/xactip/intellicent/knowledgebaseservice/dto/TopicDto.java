package com.xactip.intellicent.knowledgebaseservice.dto;

import jakarta.validation.constraints.NotBlank;

public record TopicDto(
        String id,
        @NotBlank(message = "subcategoryId field must not be empty.")
        String subcategoryId,
        @NotBlank(message = "title field must not be empty.")
        String title,
        String description
) {
}
