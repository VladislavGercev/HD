package com.gercev.converter;

import com.gercev.domain.Category;
import com.gercev.dto.CategoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    @Autowired
    private ModelMapper modelMapper;

        public CategoryDto convert(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }
    public Category convert(CategoryDto categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }
}
