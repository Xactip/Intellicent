package com.xactip.intellicent.knowledgebaseservice.service;

import com.xactip.intellicent.knowledgebaseservice.dto.CategoryDto;
import com.xactip.intellicent.knowledgebaseservice.exception.EntityNotFoundException;
import com.xactip.intellicent.knowledgebaseservice.mapping.CategoryMapper;
import com.xactip.intellicent.knowledgebaseservice.model.Category;
import com.xactip.intellicent.knowledgebaseservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper mapper;
    private final CategoryRepository repository;

    public List<CategoryDto> getAllCategories() {
        List<Category> categories = repository.findAll();
        return mapper.entityToDTO(categories);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    public CategoryDto getCategoryById(String id) {
        return repository.findById(id)
                .map(mapper::entityToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Category not found by ID " + id));
    }

    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = mapper.dtoToEntity(categoryDto);
        Category savedCategory = repository.save(category);
        return mapper.entityToDTO(savedCategory);
    }

    public CategoryDto updateCategory(String id, CategoryDto categoryDto) {
        return repository.findById(id)
                .map(category -> {
                    category.setTitle(categoryDto.title());
                    category.setDescription(categoryDto.description());
                    Category savedCategory = repository.save(category);
                    return mapper.entityToDTO(savedCategory);
                })
                .orElseThrow(() -> new EntityNotFoundException("Category not found by ID " + id));
    }

    public void deleteCategoryById(String id) {
        repository.deleteById(id);
    }
}
