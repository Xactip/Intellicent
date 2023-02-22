package com.xactip.intellicent.knowledgebaseservice.repository;

import com.xactip.intellicent.knowledgebaseservice.model.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TopicRepository extends MongoRepository<Topic, String> {
}
