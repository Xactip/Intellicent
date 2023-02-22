package com.xactip.intellicent.knowledgebaseservice.mapping;

import com.xactip.intellicent.knowledgebaseservice.dto.TopicDto;
import com.xactip.intellicent.knowledgebaseservice.model.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TopicMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "subcategoryId", target = "subcategoryId")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    TopicDto entityToDTO(Topic topic);

    List<TopicDto> entityToDTO(Iterable<Topic> topics);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "subcategoryId", target = "subcategoryId")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    Topic dtoToEntity(TopicDto topic);

    List<Topic> dtoToEntity(Iterable<TopicDto> topics);
}
