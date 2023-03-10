package com.xactip.intellicent.knowledgebaseservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("topics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    @Id
    private String id;
    private String subcategoryId;
    private String title;
    private String description;
}
