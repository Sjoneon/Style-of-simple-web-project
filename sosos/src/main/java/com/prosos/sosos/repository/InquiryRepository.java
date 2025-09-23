package com.prosos.sosos.repository;

import com.prosos.sosos.model.Inquiry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findByUserId(Long userId); // 사용자별 문의 조회
    List<Inquiry> findByAnswerIsNull(); // 답변이 없는 문의 조회
}
