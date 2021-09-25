package com.gercev.service;

import com.gercev.domain.Category;
import com.gercev.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    public CategoryRepository categoryRepository;

    public long addCategory(Category category) {
        return (long) categoryRepository.addCategory(category);
    }
}
