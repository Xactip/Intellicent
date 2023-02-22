package com.xactip.intellicent.knowledgebaseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class KnowledgeBaseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowledgeBaseServiceApplication.class, args);
    }

}
