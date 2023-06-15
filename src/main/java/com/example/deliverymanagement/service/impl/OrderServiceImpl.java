package com.example.deliverymanagement.service.impl;

import com.example.deliverymanagement.dto.request.OrderRequestDto;
import com.example.deliverymanagement.dto.response.OrderResponseDto;
import com.example.deliverymanagement.dto.response.ResponseDto;
import com.example.deliverymanagement.entity.*;
import com.example.deliverymanagement.repository.CompanyRepository;
import com.example.deliverymanagement.repository.OrderRepository;
import com.example.deliverymanagement.service.CartService;
import com.example.deliverymanagement.service.DriverService;
import com.example.deliverymanagement.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final DriverService driverService;
    private final CartService cartService;

    @Override
    public ResponseDto save(Cart cart, Customer customer, OrderRequestDto orderRequestDto) {
        Company company=companyRepository.getCompanyById(1L);
        company.setTotalBudget(cart.getTotalAmount());
        companyRepository.save(company);

        Delivery_order order = orderRepository.save(modelMapper.map(orderRequestDto,Delivery_order.class));
        if (order!=null){
            order.setCart(cart);
            order.setCustomer(customer);
            order.setDate(Date.valueOf(LocalDate.now()));
            Driver driver=driverService.search();
            if (driver!=null){
                order.setDriver(driver);
                orderRepository.save(order);
                cartService.deleteFoodsInCart(cart.getId());
                return new ResponseDto("Order confirmed");
            }else {
                return new ResponseDto("Drivers is Busy");
            }
        }else {
            return new ResponseDto("Save is unsuccessfully!!!");
        }
    }

    @Override
    public ResponseDto delete(Long id) {
        Delivery_order order=orderRepository.getDelivery_orderById(id);
        order.setStatus(0);
        Delivery_order save = orderRepository.save(order);
        return save!=null? new ResponseDto("Deleting is successfull"):
                new ResponseDto("Deleting is unsuccessfull!!!");
    }

    @Override
    public OrderResponseDto getById(Long id) {
        Delivery_order order=orderRepository.getDelivery_orderById(id);
        if (order.getStatus()==1){
            return modelMapper.map(orderRepository.getDelivery_orderById(id),OrderResponseDto.class);
        }else {
            throw new RuntimeException("Order Not found");
        }
    }

    @Override
    public List<OrderResponseDto> getAll() {
        return orderRepository.findAll().stream()
                .filter(order->order.getStatus()==1)
                .map(order ->modelMapper.map(order,OrderResponseDto.class))
                .collect(Collectors.toList());
    }
}
