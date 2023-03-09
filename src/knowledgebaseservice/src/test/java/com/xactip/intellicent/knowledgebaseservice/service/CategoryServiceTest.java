package com.xactip.intellicent.knowledgebaseservice.service;

import com.xactip.intellicent.knowledgebaseservice.dto.CategoryDto;
import com.xactip.intellicent.knowledgebaseservice.exception.EntityNotFoundException;
import com.xactip.intellicent.knowledgebaseservice.exception.ReferenceNotFoundException;
import com.xactip.intellicent.knowledgebaseservice.mapping.CategoryMapper;
import com.xactip.intellicent.knowledgebaseservice.model.Category;
import com.xactip.intellicent.knowledgebaseservice.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Spy
    private CategoryMapper mapper = Mappers.getMapper(CategoryMapper.class);
    @Mock
    private CategoryRepository repository;
    @InjectMocks
    private CategoryService categoryService;

    @Test
    void testGetAllCategories_ReturnsAllCategories() {
        List<Category> entities = Arrays.asList(
                new Category("id_1", "title_1", "description_1"),
                new Category("id_2", "title_2", "description_2")
        );
        List<CategoryDto> dtos = Arrays.asList(
                new CategoryDto("id_1", "title_1", "description_1"),
                new CategoryDto("id_2", "title_2", "description_2")
        );
        when(repository.findAll()).thenReturn(entities);

        List<CategoryDto> result = categoryService.getAllCategories();

        assertThat(result, equalTo(dtos));
    }

    @Test
    void testVerifyIfCategoryExists_CategoryFound() {
        String id = "id";
        when(repository.existsById(id)).thenReturn(true);

        categoryService.verifyIfCategoryExists(id);
        verify(repository, times(1)).existsById(id);
    }

    @Test
    void testVerifyIfCategoryExists_CategoryNotFound_ThrowsNotFoundException() {
        String id = "id";
        when(repository.existsById(id)).thenReturn(false);

        Exception exception = assertThrows(ReferenceNotFoundException.class, () -> categoryService.verifyIfCategoryExists(id));
        assertThat(exception.getMessage(), is("Referenced category not found by ID " + id));
    }

    @Test
    void testGetCategoryById_CategoryFound_ReturnsCategory() {
        String id = "id";
        Category entity = new Category(id, "title", "description");
        CategoryDto dto = new CategoryDto(id, "title", "description");
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        CategoryDto result = categoryService.getCategoryById(id);

        assertThat(result, equalTo(dto));
    }

    @Test
    void testGetCategoryById_CategoryNotFound_ThrowsNotFoundException() {
        String id = "id";
        when(repository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> categoryService.getCategoryById(id));
        assertThat(exception.getMessage(), is("Category not found by ID " + id));
    }

    @Test
    void testAddCategory_CategoryAdded_ReturnsSavedCategory() {
        CategoryDto dto = new CategoryDto("id", "title", "description");
        when(repository.save(any())).then(returnsFirstArg());

        CategoryDto result = categoryService.addCategory(dto);

        assertThat(result, equalTo(dto));
        verify(repository, times(1)).save(any());
    }

    @Test
    void testUpdateCategory_CategoryFoundAndUpdated_ReturnsSavedCategory() {
        String id = "id";
        CategoryDto dto = new CategoryDto(id, "title_new", "description_new");
        Category entity = new Category(id, "title_old", "description_old");
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.save(any())).then(returnsFirstArg());

        CategoryDto result = categoryService.updateCategory(id, dto);

        assertThat(result, equalTo(dto));
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(any());
    }

    @Test
    void testUpdateCategory_CategoryNotFound_ThrowsNotFoundException() {
        String id = "id";
        CategoryDto dto = new CategoryDto(id, "title_new", "description_new");
        when(repository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> categoryService.updateCategory(id, dto));
        assertThat(exception.getMessage(), is("Category not found by ID " + id));
    }

    @Test
    void testDeleteCategoryById_RepositoryMethodCalled() {
        String id = "id";

        categoryService.deleteCategoryById(id);

        verify(repository, times(1)).deleteById(id);
    }
}