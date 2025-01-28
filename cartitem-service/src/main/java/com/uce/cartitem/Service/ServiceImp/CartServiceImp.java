package com.uce.cartitem.Service.ServiceImp;

import java.util.List;

import com.uce.cartitem.Model.CartItem;
import com.uce.cartitem.Model.Dto.CartItemDto;

public interface CartServiceImp {

    void savItem(CartItem cartItem);

    void removeFromCart(CartItem cartItem);

    List<CartItemDto> getAllItems();
}
