package com.xactip.intellicent.knowledgebaseservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("subcategories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subcategory {
    @Id
    private String id;
    private String categoryId;
    private String title;
    private String description;
}
