package com.xactip.intellicent.usermanagementservice.model;

import com.xactip.intellicent.usermanagementservice.model.enums.TopicStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("topic_events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicEvent {

    @Id
    private String id;
    private String userId;
    private String topicId;
    private TopicStatus status;
    private LocalDateTime startedWhen;
    private LocalDateTime completedWhen;
}
