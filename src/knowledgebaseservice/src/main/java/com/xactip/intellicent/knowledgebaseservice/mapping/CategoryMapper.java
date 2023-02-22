package com.xactip.intellicent.knowledgebaseservice.mapping;

import com.xactip.intellicent.knowledgebaseservice.dto.CategoryDto;
import com.xactip.intellicent.knowledgebaseservice.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    CategoryDto entityToDTO(Category category);

    List<CategoryDto> entityToDTO(Iterable<Category> categories);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    Category dtoToEntity(CategoryDto category);

    List<Category> dtoToEntity(Iterable<CategoryDto> categories);
}
