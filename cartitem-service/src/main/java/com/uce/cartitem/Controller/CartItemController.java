package com.uce.cartitem.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uce.cartitem.Model.CartItem;
import com.uce.cartitem.Model.Dto.CartItemDto;
import com.uce.cartitem.Service.CartService;

@RestController
@RequestMapping("/items")
public class CartItemController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestBody CartItem cartItem) {
        try {
            cartService.savItem(cartItem);
            return ResponseEntity.ok("Item added to cart successfully.");
        } catch (RuntimeException e) {
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> removeFromCart(@RequestBody CartItem cartItem) {
        try {
            cartService.removeFromCart(cartItem);
            return ResponseEntity.ok("Item removed from cart successfully.");
        } catch (RuntimeException e) {
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CartItemDto>> getAllItems() {
        List<CartItemDto> items = cartService.getAllItems();
        return ResponseEntity.ok(items);
    }
}
