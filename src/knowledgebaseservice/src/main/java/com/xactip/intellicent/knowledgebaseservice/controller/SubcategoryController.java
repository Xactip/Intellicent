package com.xactip.intellicent.knowledgebaseservice.controller;

import com.xactip.intellicent.knowledgebaseservice.dto.SubcategoryDto;
import com.xactip.intellicent.knowledgebaseservice.service.SubcategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subcategories")
@RequiredArgsConstructor
public class SubcategoryController {

    private final SubcategoryService service;

    @GetMapping("/")
    public ResponseEntity<List<SubcategoryDto>> getAllSubcategories() {
        List<SubcategoryDto> categories = service.getAllSubcategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubcategoryDto> getSubcategoryById(@PathVariable("id") String id) {
        SubcategoryDto subcategory = service.getSubcategoryById(id);
        return ResponseEntity.ok(subcategory);
    }

    @PostMapping("/")
    public ResponseEntity<SubcategoryDto> addSubcategory(@Valid @RequestBody SubcategoryDto subcategoryDto) {
        SubcategoryDto createdSubcategory = service.addSubcategory(subcategoryDto);
        return ResponseEntity.ok(createdSubcategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubcategoryDto> updateSubcategory(@PathVariable("id") String id,
                                                            @Valid @RequestBody SubcategoryDto subcategoryDto) {
        SubcategoryDto updatedSubcategory = service.updateSubcategory(id, subcategoryDto);
        return ResponseEntity.ok(updatedSubcategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubcategoryById(@PathVariable("id") String id) {
        service.deleteSubcategoryById(id);
        return ResponseEntity.noContent().build();
    }
}
