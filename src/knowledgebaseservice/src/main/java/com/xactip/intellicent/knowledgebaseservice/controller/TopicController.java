package com.xactip.intellicent.knowledgebaseservice.controller;

import com.xactip.intellicent.knowledgebaseservice.dto.TopicDto;
import com.xactip.intellicent.knowledgebaseservice.service.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService service;

    @GetMapping("/topics")
    public ResponseEntity<List<TopicDto>> getAllTopics() {
        List<TopicDto> categories = service.getAllTopics();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/topics/{id}")
    public ResponseEntity<TopicDto> getTopicById(@PathVariable("id") String id) {
        TopicDto topic = service.getTopicById(id);
        return ResponseEntity.ok(topic);
    }

    @PostMapping("/topics")
    public ResponseEntity<TopicDto> addTopic(@Valid @RequestBody TopicDto topicDto) {
        TopicDto createdTopicDto = service.addTopic(topicDto);
        return ResponseEntity.ok(createdTopicDto);
    }

    @PutMapping("/topics/{id}")
    public ResponseEntity<TopicDto> updateTopic(@PathVariable("id") String id,
                                                @Valid @RequestBody TopicDto topicDto) {
        TopicDto updatedTopic = service.updateTopic(id, topicDto);
        return ResponseEntity.ok(updatedTopic);
    }

    @DeleteMapping("/topics/{id}")
    public ResponseEntity<Void> deleteTopicById(@PathVariable("id") String id) {
        service.deleteTopicById(id);
        return ResponseEntity.noContent().build();
    }
}
