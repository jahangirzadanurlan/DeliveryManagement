package com.example.deliverymanagement.service.impl;

import com.example.deliverymanagement.dto.request.CategoryRequestDto;
import com.example.deliverymanagement.dto.response.CategoryResponseDto;
import com.example.deliverymanagement.dto.response.ResponseDto;
import com.example.deliverymanagement.entity.Category;
import com.example.deliverymanagement.repository.CategoryRepository;
import com.example.deliverymanagement.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    @Override
    public ResponseDto save(CategoryRequestDto categoryRequestDto) {
        Category save = categoryRepository.save(modelMapper.map(categoryRequestDto, Category.class));
        return save!=null?new ResponseDto("Save is successfully!"):
                new ResponseDto("Save is unsuccessfully!!!");
    }

    @Override
    public ResponseDto put(Long id, CategoryRequestDto categoryRequestDto) {
        Category category=categoryRepository.getCategoryById(id);
        if (category!=null){
            category.setName(categoryRequestDto.getName());
            category.setDescription(categoryRequestDto.getDescription());
            categoryRepository.save(category);
            return new ResponseDto("Updating successfull!");
        }else {
            return new ResponseDto("Category not found!!!");
        }
    }

    @Override
    public ResponseDto delete(Long id) {
        Category category=categoryRepository.getCategoryById(id);
        if (category!=null){
            categoryRepository.delete(category);
            return new ResponseDto("Deleting successfull!");
        }else {
            return new ResponseDto("Food is not found!!! ");
        }
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        return modelMapper.map(categoryRepository.getCategoryById(id), CategoryResponseDto.class);
    }

    @Override
    public List<CategoryResponseDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category,CategoryResponseDto.class))
                .collect(Collectors.toList());
    }
}
