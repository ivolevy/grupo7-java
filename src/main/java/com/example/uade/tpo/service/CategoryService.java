package com.example.uade.tpo.service;

import com.example.uade.tpo.Utils.Mapper;
import com.example.uade.tpo.dtos.request.CategoryRequestDto;
import com.example.uade.tpo.dtos.response.CategoryResponseDto;
import com.example.uade.tpo.entity.Category;
import com.example.uade.tpo.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    public List<CategoryResponseDto> getCategories() {
        return categoryRepository.findAll().stream().map(Mapper::convertToCategoryResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<CategoryResponseDto> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).map(Mapper::convertToCategoryResponseDto);
    }

    public CategoryResponseDto createCategory(CategoryRequestDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        return Mapper.convertToCategoryResponseDto(categoryRepository.save(category));
    }

    public CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryDetails) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setName(categoryDetails.getName());
            return Mapper.convertToCategoryResponseDto(categoryRepository.save(category));
        }
        return null;
    }
}
