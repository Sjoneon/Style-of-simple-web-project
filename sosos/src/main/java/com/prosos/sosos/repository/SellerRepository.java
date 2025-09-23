package com.prosos.sosos.repository;

import com.prosos.sosos.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    // 사업자 번호로 판매자 조회
    Seller findByBusinessNumber(String businessNumber);
}
