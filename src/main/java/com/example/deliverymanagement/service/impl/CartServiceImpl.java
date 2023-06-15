package com.example.deliverymanagement.service.impl;

import com.example.deliverymanagement.dto.request.CartRequestDto;
import com.example.deliverymanagement.dto.response.ResponseDto;
import com.example.deliverymanagement.entity.Cart;
import com.example.deliverymanagement.exceptions.CartNotFoundException;
import com.example.deliverymanagement.repository.CartRepository;
import com.example.deliverymanagement.service.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    @Override
    public ResponseDto save(Cart cart) {
        Cart save = cartRepository.save(cart);
        if (save!=null){
            return new ResponseDto("Save is successfull!");
        }else {
            throw new CartNotFoundException();
        }
    }

    @Override
    public ResponseDto deleteFoodsInCart(Long id) {
       Cart cart= cartRepository.findCartByCustomerId(id);
       cart.getFoods().clear();
        Cart save = cartRepository.save(cart);
        if (save!=null){
            return new ResponseDto("Cart foods deleted!");
        }else {
            throw new CartNotFoundException();
        }
    }


}
