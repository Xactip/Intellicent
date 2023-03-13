package com.xactip.intellicent.knowledgebaseservice.controller;

import com.c4_soft.springaddons.security.oauth2.test.annotations.OpenId;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xactip.intellicent.knowledgebaseservice.dto.CategoryDto;
import com.xactip.intellicent.knowledgebaseservice.model.Category;
import com.xactip.intellicent.knowledgebaseservice.repository.CategoryRepository;
import com.xactip.intellicent.knowledgebaseservice.tc.TestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CategoryControllerIT extends TestContainer {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CategoryRepository repository;

    @BeforeEach
    void beforeEach() {
        repository.deleteAll();
    }

    @Test
    @OpenId(authorities = "ROLE_user")
    void getAllCategories() throws Exception {
        List<Category> categories = Arrays.asList(
                Category.builder().title("test_title_1").description("test_description_1").build(),
                Category.builder().title("test_title_2").description("test_description_2").build()
        );
        repository.saveAll(categories);

        MockHttpServletRequestBuilder request = get("/api/v1/categories").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(categories.size())))
                .andDo(print());
    }

    @Test
    @OpenId(authorities = "ROLE_user")
    void getCategoryById() throws Exception {
        Category category = Category.builder().title("test_title").description("test_description").build();
        repository.save(category);

        MockHttpServletRequestBuilder request = get("/api/v1/categories/{id}", category.getId())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(category.getTitle())))
                .andExpect(jsonPath("$.description", is(category.getDescription())))
                .andDo(print());
    }

    @Test
    @OpenId(authorities = "ROLE_user")
    void getCategoryById_notFound() throws Exception {
        String categoryId = "1";
        Category category = Category.builder().title("test_title").description("test_description").build();
        repository.save(category);

        MockHttpServletRequestBuilder request = get("/api/v1/categories/{id}", categoryId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @OpenId(authorities = "ROLE_user")
    void addCategory() throws Exception {
        CategoryDto category = new CategoryDto(null, "test_title", "test_description");

        MockHttpServletRequestBuilder request = post("/api/v1/categories")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category));

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is(category.title())))
                .andExpect(jsonPath("$.description", is(category.description())))
                .andDo(print());
    }

    @Test
    @OpenId(authorities = "ROLE_user")
    void updateCategory() throws Exception {
        Category category = Category.builder().title("test_title_old").description("test_description_old").build();
        repository.save(category);
        CategoryDto newCategory = new CategoryDto(null, "test_title_new", "test_description_new");

        MockHttpServletRequestBuilder request = put("/api/v1/categories/{id}", category.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCategory));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(newCategory.title())))
                .andExpect(jsonPath("$.description", is(newCategory.description())))
                .andDo(print());
    }

    @Test
    @OpenId(authorities = "ROLE_user")
    void updateCategory_notFound() throws Exception {
        String categoryId = "1";
        Category category = Category.builder().title("test_title_old").description("test_description_old").build();
        repository.save(category);
        CategoryDto newCategory = new CategoryDto(null, "test_title_new", "test_description_new");

        MockHttpServletRequestBuilder request = put("/api/v1/categories/{id}", categoryId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCategory));

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @OpenId(authorities = "ROLE_user")
    void deleteCategoryById() throws Exception {
        Category category = Category.builder().title("test_title").description("test_description").build();
        repository.save(category);

        MockHttpServletRequestBuilder request = delete("/api/v1/categories/{id}", category.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}