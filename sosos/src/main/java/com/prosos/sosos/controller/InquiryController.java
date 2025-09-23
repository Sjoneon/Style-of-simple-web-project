package com.prosos.sosos.controller;

import com.prosos.sosos.dto.InquiryDto;
import com.prosos.sosos.model.Inquiry;
import com.prosos.sosos.model.Seller;
import com.prosos.sosos.model.User;
import com.prosos.sosos.repository.InquiryRepository;
import com.prosos.sosos.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {

    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;

    public InquiryController(InquiryRepository inquiryRepository, UserRepository userRepository) {
        this.inquiryRepository = inquiryRepository;
        this.userRepository = userRepository;
    }

    // 전체 문의 목록 조회 (사용자 및 판매자용)
    @GetMapping
    public ResponseEntity<List<InquiryDto>> getInquiries() {
        List<InquiryDto> inquiries = inquiryRepository.findAll().stream()
                .map(inquiry -> new InquiryDto(
                        inquiry.getId(),
                        inquiry.getUserId(),
                        userRepository.findById(inquiry.getUserId())
                                      .map(user -> user.getName())
                                      .orElse("Unknown"),
                        inquiry.getTitle(),
                        inquiry.getContent(),
                        inquiry.getAnswer(),
                        inquiry.getCreatedDate(),
                        inquiry.getAnsweredDate()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(inquiries);
    }

    // 사용자별 문의 목록 조회
    @GetMapping("/user")
    public ResponseEntity<List<Inquiry>> getUserInquiries(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Inquiry> inquiries = inquiryRepository.findByUserId(user.getId());
        return ResponseEntity.ok(inquiries);
    }

    // 문의 작성
    @PostMapping
    public ResponseEntity<Void> createInquiry(HttpSession session, @RequestBody Inquiry inquiry) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        inquiry.setUserId(user.getId());
        inquiry.setCreatedDate(LocalDateTime.now());
        inquiryRepository.save(inquiry);
        return ResponseEntity.ok().build();
    }

    // 문의 답변 작성
    @PutMapping("/{inquiryId}/answer")
    public ResponseEntity<Void> answerInquiry(@PathVariable Long inquiryId, @RequestBody String answer, HttpSession session) {
        Seller seller = (Seller) session.getAttribute("loggedInUser");
        if (seller == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("문의 ID를 찾을 수 없습니다."));
        inquiry.setAnswer(answer);
        inquiry.setSellerName("Sos운영자");
        inquiry.setAnsweredDate(LocalDateTime.now());
        inquiryRepository.save(inquiry);
        return ResponseEntity.ok().build();
    }


    // 문의 답변 삭제
    @DeleteMapping("/{inquiryId}")
    public ResponseEntity<Void> deleteInquiry(@PathVariable Long inquiryId, HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("문의 ID를 찾을 수 없습니다."));

        // 판매자일 경우 모든 문의 삭제 가능
        if (loggedInUser instanceof Seller) {
            inquiryRepository.deleteById(inquiryId);
            return ResponseEntity.noContent().build();
        }

        // 일반 사용자일 경우 본인의 문의만 삭제 가능
        if (loggedInUser instanceof User) {
            User user = (User) loggedInUser;
            if (!inquiry.getUserId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            inquiryRepository.deleteById(inquiryId);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    // 문의 답변 수정
    @PutMapping("/{inquiryId}/answer/update")
    public ResponseEntity<Void> updateInquiryAnswer(@PathVariable Long inquiryId, @RequestBody String newAnswer, HttpSession session) {
        Seller seller = (Seller) session.getAttribute("loggedInUser");
        if (seller == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("문의 ID를 찾을 수 없습니다."));
        inquiry.setAnswer(newAnswer);
        inquiry.setAnsweredDate(LocalDateTime.now());
        inquiryRepository.save(inquiry);
        return ResponseEntity.ok().build();
    }
}
