package com.uce.cartitem.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.uce.cartitem.Model.CartItem;
import com.uce.cartitem.Model.Dto.CartItemDto;
import com.uce.cartitem.Model.Dto.ProductDto;
import com.uce.cartitem.Model.Dto.UserDto;
import com.uce.cartitem.Repository.CartRepository;
import com.uce.cartitem.Service.ServiceImp.CartServiceImp;

@Service
public class CartService implements CartServiceImp {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;

    @Value("${product.service.url}")
    private String productServiceUrl;

    private CartItemDto converToDTO(CartItem item) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setCartItem(item);
        ProductDto product = restTemplate.getForObject(productServiceUrl + item.getProductId(),
                ProductDto.class);
        cartItemDto.setProduct(product);

        UserDto user = restTemplate.getForObject(
                userServiceUrl + item.getUserId(),
                UserDto.class);
        cartItemDto.setUser(user);

        return cartItemDto;
    }

    private boolean isUserEnabled(String userId) {
        UserDto user = restTemplate.getForObject(userServiceUrl + "/" + userId, UserDto.class);
        return user != null && user.isEnabled();
    }

    @Override
    public void savItem(CartItem cartItem) {
        if (!isUserEnabled(cartItem.getUserId())) {
            throw new RuntimeException("User account is not activated.");
        }

        ProductDto product = restTemplate.getForObject(productServiceUrl + "/" + cartItem.getProductId(),
                ProductDto.class);
        if (product == null) {
            throw new RuntimeException("El producto no se encontró.");
        }

        if (product.getStock() < cartItem.getAmount()) {
            throw new RuntimeException("No hay suficiente stock disponible para el producto: " + product.getName()
                    + ". Stock disponible: " + product.getStock());
        }

        restTemplate.put(productServiceUrl + "/" + cartItem.getProductId() + "/reduceStock", cartItem.getAmount());

        double totalAmount = cartItem.getAmount() * product.getPrice();
        cartItem.setTotalAmount(totalAmount);

        Optional<CartItem> existingItem = cartRepository.findByProductIdAndUserId(cartItem.getProductId(),
                cartItem.getUserId());
        if (existingItem.isPresent()) {
            CartItem existingCartItem = existingItem.get();
            existingCartItem.setAmount(existingCartItem.getAmount() + cartItem.getAmount());
            cartRepository.save(existingCartItem);
        } else {
            cartRepository.save(cartItem);
        }
    }

    @Override
    public void removeFromCart(CartItem cartItem) {
        if (!isUserEnabled(cartItem.getUserId())) {
            throw new RuntimeException("User account is not activated.");
        }

        restTemplate.put(productServiceUrl + "/" + cartItem.getProductId() + "/increaseStock", cartItem.getAmount());

        Optional<CartItem> existingItem = cartRepository.findByProductIdAndUserId(cartItem.getProductId(),
                cartItem.getUserId());
        if (existingItem.isPresent()) {
            CartItem existingCartItem = existingItem.get();
            if (existingCartItem.getAmount() <= cartItem.getAmount()) {
                cartRepository.delete(existingCartItem);
            } else {
                existingCartItem.setAmount(existingCartItem.getAmount() - cartItem.getAmount());
                cartRepository.save(existingCartItem);
            }
        }
    }

    @Override
    public List<CartItemDto> getAllItems() {
        List<CartItem> cartItems = cartRepository.findAll();
        return cartItems.stream().map(this::converToDTO)
                .collect(Collectors.toList());
    }

}
