package com.example.deliverymanagement.service.impl;

import com.example.deliverymanagement.dto.request.FoodRequestDto;
import com.example.deliverymanagement.dto.response.FoodResponseDto;
import com.example.deliverymanagement.dto.response.ResponseDto;
import com.example.deliverymanagement.entity.Category;
import com.example.deliverymanagement.entity.Food;
import com.example.deliverymanagement.repository.CategoryRepository;
import com.example.deliverymanagement.repository.FoodRepository;
import com.example.deliverymanagement.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseDto save(Long cat_id,FoodRequestDto foodRequestDto) {
        Food food = modelMapper.map(foodRequestDto, Food.class);
        Category category = categoryRepository.findById(cat_id).orElse(null);
        if (category != null) {
            food.setCategory(category);
            foodRepository.save(food);
            return new ResponseDto("Save is successful!");
        } else {
            return new ResponseDto("Save is unsuccessful!");
        }
    }

    @Override
    public ResponseDto put(Long id,FoodRequestDto foodRequestDto) {
        Food food=foodRepository.getFoodById(id);
        if (food!=null){
            food.setName(foodRequestDto.getName());
            food.setDescription(foodRequestDto.getDescription());
            food.setAmount(foodRequestDto.getAmount());
            foodRepository.save(food);
            return new ResponseDto("Updating successfull!");
        }else {
            return new ResponseDto("Food not found!!!");
        }
    }

    @Override
    public ResponseDto delete(Long id) {
        Food food=foodRepository.getFoodById(id);
        if (food!=null){
            foodRepository.delete(food);
            return new ResponseDto("Deleting successfull!");
        }else {
            return new ResponseDto("Food is not found!!! ");
        }
    }

    @Override
    public FoodResponseDto getById(Long id) {
        return modelMapper.map(foodRepository.getFoodById(id),FoodResponseDto.class);
    }

    @Override
    public List<FoodResponseDto> foods() {
        return foodRepository.findAll().stream()
                .map(food -> modelMapper.map(food,FoodResponseDto.class))
                .collect(Collectors.toList());
    }
}
