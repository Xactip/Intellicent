package com.xactip.intellicent.knowledgebaseservice.service;

import com.xactip.intellicent.knowledgebaseservice.dto.SubcategoryDto;
import com.xactip.intellicent.knowledgebaseservice.exception.EntityNotFoundException;
import com.xactip.intellicent.knowledgebaseservice.exception.ReferenceNotFoundException;
import com.xactip.intellicent.knowledgebaseservice.mapping.SubcategoryMapper;
import com.xactip.intellicent.knowledgebaseservice.model.Subcategory;
import com.xactip.intellicent.knowledgebaseservice.repository.SubcategoryRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubcategoryServiceTest {

    @Spy
    private SubcategoryMapper mapper = Mappers.getMapper(SubcategoryMapper.class);
    @Mock
    private SubcategoryRepository repository;
    @Mock
    private CategoryService categoryService;
    @InjectMocks
    private SubcategoryService subcategoryService;

    @Test
    void testGetAllSubcategories_ReturnsAllSubcategories() {
        List<Subcategory> entities = Arrays.asList(
                new Subcategory("id_1", "category_id_1", "title_1", "description_1"),
                new Subcategory("id_2", "category_id_2", "title_2", "description_2")
        );
        List<SubcategoryDto> dtos = Arrays.asList(
                new SubcategoryDto("id_1", "category_id_1", "title_1", "description_1"),
                new SubcategoryDto("id_2", "category_id_2", "title_2", "description_2")
        );
        when(repository.findAll()).thenReturn(entities);

        List<SubcategoryDto> result = subcategoryService.getAllSubcategories();

        assertThat(result, equalTo(dtos));
    }

    @Test
    void testVerifyIfSubcategoryExists_SubcategoryFound() {
        String id = "id";
        when(repository.existsById(id)).thenReturn(true);

        subcategoryService.verifyIfSubcategoryExists(id);
        verify(repository, times(1)).existsById(id);
    }

    @Test
    void testVerifyIfSubcategoryExists_SubcategoryNotFound_ThrowsNotFoundException() {
        String id = "id";
        when(repository.existsById(id)).thenReturn(false);

        Exception exception = assertThrows(ReferenceNotFoundException.class, () -> subcategoryService.verifyIfSubcategoryExists(id));
        assertThat(exception.getMessage(), is("Referenced subcategory not found by ID " + id));
    }

    @Test
    void testGetSubcategoryById_SubcategoryFound_ReturnsSubcategory() {
        String id = "id";
        Subcategory entity = new Subcategory(id, "category_id", "title", "description");
        SubcategoryDto dto = new SubcategoryDto(id, "category_id", "title", "description");
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        SubcategoryDto result = subcategoryService.getSubcategoryById(id);

        assertThat(result, equalTo(dto));
    }

    @Test
    void testGetSubcategoryById_SubcategoryNotFound_ThrowsNotFoundException() {
        String id = "id";
        when(repository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> subcategoryService.getSubcategoryById(id));
        assertThat(exception.getMessage(), is("Subcategory not found by ID " + id));
    }

    @Test
    void testAddSubcategory_SubcategoryAdded_ReturnsSavedSubcategory() {
        SubcategoryDto dto = new SubcategoryDto("id", "category_id", "title", "description");
        when(repository.save(any())).then(returnsFirstArg());

        SubcategoryDto result = subcategoryService.addSubcategory(dto);

        assertThat(result, equalTo(dto));
        verify(categoryService, times(1)).verifyIfCategoryExists(dto.categoryId());
        verify(repository, times(1)).save(any());
    }

    @Test
    void testUpdateSubcategory_SubcategoryFoundAndUpdated_ReturnsSavedSubcategory() {
        String id = "id";
        Subcategory entity = new Subcategory(id, "category_id_old", "title_old", "description_old");
        SubcategoryDto dto = new SubcategoryDto(id, "category_id_new", "title_new", "description_new");
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.save(any())).then(returnsFirstArg());

        SubcategoryDto result = subcategoryService.updateSubcategory(id, dto);

        assertThat(result, equalTo(dto));
        verify(repository, times(1)).findById(id);
        verify(categoryService, times(1)).verifyIfCategoryExists(dto.categoryId());
        verify(repository, times(1)).save(any());
    }

    @Test
    void testUpdateSubcategory_SubcategoryNotFound_ThrowsNotFoundException() {
        String id = "id";
        SubcategoryDto dto = new SubcategoryDto(id, "category_id_new", "title_new", "description_new");
        when(repository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> subcategoryService.updateSubcategory(id, dto));
        assertThat(exception.getMessage(), is("Subcategory not found by ID " + id));
    }

    @Test
    void testDeleteSubcategoryById_RepositoryMethodCalled() {
        String id = "id";

        subcategoryService.deleteSubcategoryById(id);

        verify(repository, times(1)).deleteById(id);
    }
}