package com.xactip.intellicent.usermanagementservice.dto;

import com.xactip.intellicent.usermanagementservice.model.enums.TopicStatus;

import java.time.LocalDateTime;

public record TopicEventDto(
        String id,
        String userId,
        String topicId,
        TopicStatus status,
        LocalDateTime startedWhen,
        LocalDateTime completedWhen
) {
}
