package com.xactip.intellicent.usermanagementservice.mapping;

import com.xactip.intellicent.usermanagementservice.dto.TopicEventDto;
import com.xactip.intellicent.usermanagementservice.model.TopicEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TopicEventMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "topicId", target = "topicId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "startedWhen", target = "startedWhen")
    @Mapping(source = "completedWhen", target = "completedWhen")
    TopicEventDto entityToDTO(TopicEvent topicEvent);

    List<TopicEventDto> entityToDTO(Iterable<TopicEvent> topicsEvents);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "topicId", target = "topicId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "startedWhen", target = "startedWhen")
    @Mapping(source = "completedWhen", target = "completedWhen")
    TopicEvent dtoToEntity(TopicEventDto topicEvent);

    List<TopicEvent> dtoToEntity(Iterable<TopicEventDto> topicsEvents);
}
