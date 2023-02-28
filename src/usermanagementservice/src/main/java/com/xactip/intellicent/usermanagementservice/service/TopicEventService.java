package com.xactip.intellicent.usermanagementservice.service;

import com.xactip.intellicent.usermanagementservice.dto.TopicEventDto;
import com.xactip.intellicent.usermanagementservice.mapping.TopicEventMapper;
import com.xactip.intellicent.usermanagementservice.model.TopicEvent;
import com.xactip.intellicent.usermanagementservice.model.enums.TopicStatus;
import com.xactip.intellicent.usermanagementservice.repository.TopicEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicEventService {

    private final TopicEventRepository repository;
    private final TopicEventMapper mapper;

    public List<TopicEventDto> getUserTopicEvents(String userId) {
        List<TopicEvent> topicEvents = repository.findByUserId(userId);
        return mapper.entityToDTO(topicEvents);
    }

    public List<TopicEventDto> getUserTopicEventsByStatus(String userId, TopicStatus status) {
        List<TopicEvent> topicEvents = repository.findByUserIdAndStatus(userId, status);
        return mapper.entityToDTO(topicEvents);
    }

    public void startTopic(String userId, String topicId) {
        TopicEvent topicEvent = repository.findByUserIdAndTopicId(userId, topicId)
                .orElseGet(() -> TopicEvent.builder()
                        .userId(userId)
                        .topicId(topicId)
                        .build());
        topicEvent.setStatus(TopicStatus.STARTED);
        topicEvent.setStartedWhen(LocalDateTime.now());
        repository.save(topicEvent);
    }

    public void completeTopic(String userId, String topicId) {
        TopicEvent topicEvent = repository.findByUserIdAndTopicId(userId, topicId)
                .orElseGet(() -> TopicEvent.builder()
                        .userId(userId)
                        .topicId(topicId)
                        .build());
        topicEvent.setStatus(TopicStatus.COMPLETED);
        topicEvent.setCompletedWhen(LocalDateTime.now());
        repository.save(topicEvent);
    }
}
