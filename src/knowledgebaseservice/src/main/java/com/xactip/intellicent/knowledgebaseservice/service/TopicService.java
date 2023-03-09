package com.xactip.intellicent.knowledgebaseservice.service;

import com.xactip.intellicent.knowledgebaseservice.dto.TopicDto;
import com.xactip.intellicent.knowledgebaseservice.exception.EntityNotFoundException;
import com.xactip.intellicent.knowledgebaseservice.mapping.TopicMapper;
import com.xactip.intellicent.knowledgebaseservice.model.Topic;
import com.xactip.intellicent.knowledgebaseservice.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicMapper mapper;
    private final TopicRepository repository;
    private final SubcategoryService subcategoryService;

    public List<TopicDto> getAllTopics() {
        List<Topic> topics = repository.findAll();
        return mapper.entityToDTO(topics);
    }

    public TopicDto getTopicById(String id) {
        return repository.findById(id)
                .map(mapper::entityToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Topic not found by ID " + id));
    }

    public TopicDto addTopic(TopicDto topicDto) {
        subcategoryService.verifyIfSubcategoryExists(topicDto.subcategoryId());
        Topic topic = mapper.dtoToEntity(topicDto);
        Topic savedTopic = repository.save(topic);
        return mapper.entityToDTO(savedTopic);
    }

    public TopicDto updateTopic(String id, TopicDto topicDto) {
        return repository.findById(id)
                .map(topic -> {
                    if (!topic.getSubcategoryId().equals(topicDto.subcategoryId())) {
                        subcategoryService.verifyIfSubcategoryExists(topicDto.subcategoryId());
                    }
                    topic.setSubcategoryId(topicDto.subcategoryId());
                    topic.setTitle(topicDto.title());
                    topic.setDescription(topicDto.description());
                    Topic savedTopic = repository.save(topic);
                    return mapper.entityToDTO(savedTopic);
                })
                .orElseThrow(() -> new EntityNotFoundException("Topic not found by ID " + id));
    }

    public void deleteTopicById(String id) {
        repository.deleteById(id);
    }
}
