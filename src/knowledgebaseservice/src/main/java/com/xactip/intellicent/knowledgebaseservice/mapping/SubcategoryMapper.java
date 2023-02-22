package com.xactip.intellicent.knowledgebaseservice.mapping;

import com.xactip.intellicent.knowledgebaseservice.dto.SubcategoryDto;
import com.xactip.intellicent.knowledgebaseservice.model.Subcategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubcategoryMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    SubcategoryDto entityToDTO(Subcategory subcategory);

    List<SubcategoryDto> entityToDTO(Iterable<Subcategory> subcategories);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    Subcategory dtoToEntity(SubcategoryDto subcategory);

    List<Subcategory> dtoToEntity(Iterable<SubcategoryDto> subcategories);
}
