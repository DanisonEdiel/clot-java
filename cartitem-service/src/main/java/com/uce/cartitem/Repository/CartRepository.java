package com.uce.cartitem.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uce.cartitem.Model.CartItem;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long>{
    Optional<CartItem> findByProductIdAndUserId(Long productId, String userId);
}
