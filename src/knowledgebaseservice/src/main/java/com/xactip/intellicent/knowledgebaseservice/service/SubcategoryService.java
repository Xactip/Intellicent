package com.xactip.intellicent.knowledgebaseservice.service;

import com.xactip.intellicent.knowledgebaseservice.dto.SubcategoryDto;
import com.xactip.intellicent.knowledgebaseservice.exception.EntityNotFoundException;
import com.xactip.intellicent.knowledgebaseservice.exception.ReferenceNotFoundException;
import com.xactip.intellicent.knowledgebaseservice.mapping.SubcategoryMapper;
import com.xactip.intellicent.knowledgebaseservice.model.Subcategory;
import com.xactip.intellicent.knowledgebaseservice.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubcategoryService {

    private final SubcategoryMapper mapper;
    private final SubcategoryRepository repository;
    private final CategoryService categoryService;

    public List<SubcategoryDto> getAllSubcategories() {
        List<Subcategory> subcategories = repository.findAll();
        return mapper.entityToDTO(subcategories);
    }

    public void verifyIfSubcategoryExists(String id) {
        if (!repository.existsById(id)) {
            throw new ReferenceNotFoundException("Referenced subcategory not found by ID " + id);
        }
    }

    public SubcategoryDto getSubcategoryById(String id) {
        return repository.findById(id)
                .map(mapper::entityToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Subcategory not found by ID " + id));
    }

    public SubcategoryDto addSubcategory(SubcategoryDto subcategoryDto) {
        categoryService.verifyIfCategoryExists(subcategoryDto.categoryId());
        Subcategory subcategory = mapper.dtoToEntity(subcategoryDto);
        Subcategory savedCategory = repository.save(subcategory);
        return mapper.entityToDTO(savedCategory);
    }

    public SubcategoryDto updateSubcategory(String id, SubcategoryDto subcategoryDto) {
        return repository.findById(id)
                .map(subcategory -> {
                    if (!subcategory.getCategoryId().equals(subcategoryDto.categoryId())) {
                        categoryService.verifyIfCategoryExists(subcategoryDto.categoryId());
                    }
                    subcategory.setCategoryId(subcategoryDto.categoryId());
                    subcategory.setTitle(subcategoryDto.title());
                    subcategory.setDescription(subcategoryDto.description());
                    Subcategory savedSubcategory = repository.save(subcategory);
                    return mapper.entityToDTO(savedSubcategory);
                })
                .orElseThrow(() -> new EntityNotFoundException("Subcategory not found by ID " + id));
    }

    public void deleteSubcategoryById(String id) {
        repository.deleteById(id);
    }
}
