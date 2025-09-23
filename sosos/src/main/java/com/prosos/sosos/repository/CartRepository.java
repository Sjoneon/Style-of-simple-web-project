package com.prosos.sosos.repository;

import com.prosos.sosos.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // 특정 사용자의 장바구니 조회
    List<Cart> findByUserId(Long userId);

    // 특정 사용자의 특정 상품 장바구니 조회
    Cart findByUserIdAndProductId(Long userId, Long productId);
}

