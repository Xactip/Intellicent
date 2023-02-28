package com.xactip.intellicent.usermanagementservice.repository;

import com.xactip.intellicent.usermanagementservice.model.TopicEvent;
import com.xactip.intellicent.usermanagementservice.model.enums.TopicStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TopicEventRepository extends MongoRepository<TopicEvent, String> {
    List<TopicEvent> findByUserId(String userId);
    List<TopicEvent> findByUserIdAndStatus(String userId, TopicStatus status);
    Optional<TopicEvent> findByUserIdAndTopicId(String userId, String topicId);
}
