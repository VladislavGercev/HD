package com.gercev.service;

import com.gercev.domain.Category;
import com.gercev.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryRepository = new CategoryRepository();
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void testAddCategoryWithValue() {
        Category category = new Category();
        given(categoryRepository.addCategory(category)).willReturn(Optional.of(1L));
        Optional<Long> result = categoryService.addCategory(category);
        Assertions.assertEquals(new Long(1), result.orElse(null));
    }

    @Test
    void testAddCategoryWithoutValue() {
        Category category = new Category();
        given(categoryRepository.addCategory(category)).willReturn(Optional.empty());
        Optional<Long> result = categoryService.addCategory(category);
        Assertions.assertNull(result.orElse(null));
    }
}