package com.prosos.sosos.repository;

import com.prosos.sosos.model.Order;
import com.prosos.sosos.model.Seller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBuyerId(Long buyerId);
    // 주문 상태에 따른 주문 목록 조회
    List<Order> findByStatus(String status);

    List<Order> findByProduct_Seller(Seller seller);
    
}
