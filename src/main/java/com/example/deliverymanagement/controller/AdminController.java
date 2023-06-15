package com.example.deliverymanagement.controller;

import com.example.deliverymanagement.dto.request.CategoryRequestDto;
import com.example.deliverymanagement.dto.request.FoodRequestDto;
import com.example.deliverymanagement.dto.response.*;
import com.example.deliverymanagement.entity.Driver;
import com.example.deliverymanagement.repository.CategoryRepository;
import com.example.deliverymanagement.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final DriverService driverService;
    private final FoodService foodService;
    private final CustomerService customerService;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final OrderService orderService;

    @GetMapping("/drivers")
    public List<DriverResponseDto> getAllDrivers(){
        return driverService.getAll();
    }
    @GetMapping("/drivers/{id}")
    public DriverResponseDto driverById(@PathVariable Long id){
        return modelMapper.map(driverService.getById(id),DriverResponseDto.class);
    }
    @PutMapping("/drivers/{id}")
    public ResponseDto updateDriver(@PathVariable Long id,@RequestBody DriverResponseDto driverResponseDto){
        Driver driver=driverService.getById(id);
        if (driver!=null){
            driver.setName(driverResponseDto.getName());
            driver.setEmail(driverResponseDto.getEmail());
            driver.setPassword(driverResponseDto.getPassword());
            driverService.put(driver);
            return new ResponseDto("Update is successfully!");
        }else {
            return new ResponseDto("Driver id is wrong!!!");
        }
    }
    @DeleteMapping("/drivers/{id}")
    public ResponseDto deleteDriver(@PathVariable Long id){
        return driverService.delete(id);
    }
    @GetMapping("/customers")
    public List<CustomerResponseDto> getAllCustomers(){
        return customerService.getAll();
    }
    @GetMapping("/customers/{id}")
    public CustomerResponseDto getCustomer(@PathVariable Long id){
        return customerService.findById(id);
    }
    @GetMapping("/foods")
    public List<FoodResponseDto> allFoods(){
        return foodService.foods();
    }
    @GetMapping("/foods/{id}")
    public FoodResponseDto getFood(@PathVariable Long id){
        return foodService.getById(id);
    }
    @PostMapping("/category/{cat_id}/foods")
    public ResponseDto saveFood(@PathVariable Long cat_id, @RequestBody FoodRequestDto foodRequestDto){
        ResponseDto save = foodService.save(cat_id,foodRequestDto);
        return save!=null? new ResponseDto("Save is successfull!"):
                new ResponseDto("Save is unsuccessfull!!!");
    }
    @PutMapping("/foods/{id}")
    public ResponseDto updateFood(@PathVariable Long id,@RequestBody FoodRequestDto foodRequestDto){
        return foodService.put(id,foodRequestDto);
    }
    @DeleteMapping("/foods/{id}")
    public ResponseDto deleteFood(@PathVariable Long id){
        return foodService.delete(id);
    }
    @PostMapping("/category")
    public ResponseDto saveCategory(@RequestBody CategoryRequestDto categoryRequestDto){
        ResponseDto save = categoryService.save(categoryRequestDto);
        return save!=null? new ResponseDto("Save is successfull!"):
                new ResponseDto("Save is unsuccessfull!!!");
    }
    @PutMapping("/category/{id}")
    public ResponseDto updateCategory(@PathVariable Long id,@RequestBody CategoryRequestDto categoryRequestDto){
        return categoryService.put(id,categoryRequestDto);
    }
    @DeleteMapping("/category/{id}")
    public ResponseDto deleteCategory(@PathVariable Long id){
        return categoryService.delete(id);
    }
    @GetMapping("/category")
    public List<CategoryResponseDto> allCategory(){
        return categoryService.getAll();
    }
    @GetMapping("/category/{id}")
    public CategoryResponseDto getCategory(@PathVariable Long id){
        return categoryService.getById(id);
    }
    @GetMapping("/category/{id}/food")
    public List<FoodResponseDto> getCategoryFoods(@PathVariable Long id){
        return categoryRepository.getCategoryById(id).getFoods().stream()
                .map(food -> modelMapper.map(food,FoodResponseDto.class))
                .collect(Collectors.toList());
    }
    @GetMapping("/orders")
    public List<OrderResponseDto> getAllOrders(){
        return orderService.getAll();
    }
    @GetMapping("/orders/{id}")
    public OrderResponseDto getOrder(@PathVariable Long id){
        return orderService.getById(id);
    }
}
