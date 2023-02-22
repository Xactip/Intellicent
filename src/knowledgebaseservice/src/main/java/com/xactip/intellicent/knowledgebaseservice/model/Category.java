package com.xactip.intellicent.knowledgebaseservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("categories")
@Data
@NoArgsConstructor
public class Category {
    @Id
    private String id;
    private String title;
    private String description;
}
