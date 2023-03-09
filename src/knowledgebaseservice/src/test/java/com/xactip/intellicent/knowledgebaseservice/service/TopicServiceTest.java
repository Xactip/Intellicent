package com.xactip.intellicent.knowledgebaseservice.service;

import com.xactip.intellicent.knowledgebaseservice.dto.TopicDto;
import com.xactip.intellicent.knowledgebaseservice.exception.EntityNotFoundException;
import com.xactip.intellicent.knowledgebaseservice.mapping.TopicMapper;
import com.xactip.intellicent.knowledgebaseservice.model.Topic;
import com.xactip.intellicent.knowledgebaseservice.repository.TopicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {

    @Spy
    private TopicMapper mapper = Mappers.getMapper(TopicMapper.class);
    @Mock
    private TopicRepository repository;
    @Mock
    private SubcategoryService subcategoryService;
    @InjectMocks
    private TopicService topicService;

    @Test
    void testGetAllTopics_ReturnsAllTopics() {
        List<Topic> topics = Arrays.asList(
                new Topic("id_1", "subcategory_id_1", "title_1", "description_1"),
                new Topic("id_2", "subcategory_id_2", "title_2", "description_2")
        );
        List<TopicDto> dtos = Arrays.asList(
                new TopicDto("id_1", "subcategory_id_1", "title_1", "description_1"),
                new TopicDto("id_2", "subcategory_id_2", "title_2", "description_2")
        );
        when(repository.findAll()).thenReturn(topics);

        List<TopicDto> result = topicService.getAllTopics();

        assertThat(result, equalTo(dtos));
    }

    @Test
    void testGetTopicById_TopicFound_ReturnsTopic() {
        String id = "id";
        Topic entity = new Topic(id, "subcategory_id", "title", "description");
        TopicDto dto = new TopicDto(id, "subcategory_id", "title", "description");
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        TopicDto result = topicService.getTopicById(id);

        assertThat(result, equalTo(dto));
    }

    @Test
    void testGetTopicById_TopicNotFound_ThrowsNotFoundException() {
        String id = "id";
        when(repository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> topicService.getTopicById(id));
        assertThat(exception.getMessage(), equalTo("Topic not found by ID " + id));
    }

    @Test
    void testAddTopic_TopicAdded_ReturnsSavedTopic() {
        TopicDto dto = new TopicDto("id", "subcategory_id", "title", "description");
        when(repository.save(any())).then(returnsFirstArg());

        TopicDto result = topicService.addTopic(dto);

        assertThat(result, equalTo(dto));
        verify(subcategoryService, times(1)).verifyIfSubcategoryExists(dto.subcategoryId());
        verify(repository, times(1)).save(any());
    }

    @Test
    void testUpdateTopic_TopicFoundAndUpdated_ReturnsUpdatedTopic() {
        String id = "id";
        TopicDto dto = new TopicDto(id, "new_subcategory_id", "new_title", "new_description");
        Topic entity = new Topic(id, "old_subcategory_id", "old_title", "old_description");
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.save(any())).then(returnsFirstArg());

        TopicDto result = topicService.updateTopic(id, dto);

        assertThat(result, equalTo(dto));
        verify(repository, times(1)).findById(id);
        verify(subcategoryService, times(1)).verifyIfSubcategoryExists(dto.subcategoryId());
        verify(repository, times(1)).save(any());
    }

    @Test
    void testUpdateTopic_TopicNotFound_ThrowsNotFoundException() {
        String id = "id";
        TopicDto dto = new TopicDto(id, "new_subcategory_id", "new_title", "new_description");
        when(repository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> topicService.updateTopic(id, dto));
        assertThat(exception.getMessage(), equalTo("Topic not found by ID " + id));
    }

    @Test
    void deleteTopicById() {
        String id = "id";

        topicService.deleteTopicById(id);

        verify(repository, times(1)).deleteById(id);
    }
}