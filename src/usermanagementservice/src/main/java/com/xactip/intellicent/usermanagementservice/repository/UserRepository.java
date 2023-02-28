package com.xactip.intellicent.usermanagementservice.repository;

import com.xactip.intellicent.usermanagementservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
