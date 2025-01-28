package com.uce.cartitem.Model.Dto;

import com.uce.cartitem.Model.CartItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private ProductDto product;
    private UserDto user;
    private CartItem cartItem;
}
