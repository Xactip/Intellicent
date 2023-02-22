package com.xactip.intellicent.knowledgebaseservice.repository;

import com.xactip.intellicent.knowledgebaseservice.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
