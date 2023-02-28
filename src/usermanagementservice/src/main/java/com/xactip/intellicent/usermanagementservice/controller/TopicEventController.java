package com.xactip.intellicent.usermanagementservice.controller;

import com.xactip.intellicent.usermanagementservice.dto.TopicEventDto;
import com.xactip.intellicent.usermanagementservice.dto.UserDto;
import com.xactip.intellicent.usermanagementservice.model.enums.TopicStatus;
import com.xactip.intellicent.usermanagementservice.service.TopicEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{user_id}")
@RequiredArgsConstructor
public class TopicEventController {

    private final TopicEventService topicEventService;

    @GetMapping("/topic_events")
    public ResponseEntity<List<TopicEventDto>> getTopicEventsByUser(@PathVariable("user_id") String userId) {
        List<TopicEventDto> userTopicEvents = topicEventService.getUserTopicEvents(userId);
        return ResponseEntity.ok(userTopicEvents);
    }

    @GetMapping("/topic_events/started")
    public ResponseEntity<List<TopicEventDto>> getStartedTopicEventsByUser(@PathVariable("user_id") String userId) {
        List<TopicEventDto> userTopicEvents = topicEventService.getUserTopicEventsByStatus(userId, TopicStatus.STARTED);
        return ResponseEntity.ok(userTopicEvents);
    }

    @GetMapping("/topic_events/completed")
    public ResponseEntity<List<TopicEventDto>> getCompletedTopicEventsByUser(@PathVariable("user_id") String userId) {
        List<TopicEventDto> userTopicEvents = topicEventService.getUserTopicEventsByStatus(userId, TopicStatus.COMPLETED);
        return ResponseEntity.ok(userTopicEvents);
    }

    @PutMapping("/topic_events/{topic_id}/start")
    public ResponseEntity<UserDto> startTopicForUser(@PathVariable("user_id") String userId,
                                                     @PathVariable("topic_id") String topicId) {
        topicEventService.startTopic(userId, topicId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/topic_events/{topic_id}/complete")
    public ResponseEntity<UserDto> completeTopicForUser(@PathVariable("user_id") String userId,
                                                        @PathVariable("topic_id") String topicId) {
        topicEventService.completeTopic(userId, topicId);
        return ResponseEntity.noContent().build();
    }
}