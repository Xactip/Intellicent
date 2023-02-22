package com.xactip.intellicent.knowledgebaseservice.repository;

import com.xactip.intellicent.knowledgebaseservice.model.Subcategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubcategoryRepository extends MongoRepository<Subcategory, String> {
}
