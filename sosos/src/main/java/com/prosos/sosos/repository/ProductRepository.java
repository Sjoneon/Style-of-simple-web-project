package com.prosos.sosos.repository;

import com.prosos.sosos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 상품 이름으로 검색 (부분 일치)
    List<Product> findByNameContaining(String name);

    // 카테고리별로 분류 조회
    List<Product> findByCategory(String category);

    
}

