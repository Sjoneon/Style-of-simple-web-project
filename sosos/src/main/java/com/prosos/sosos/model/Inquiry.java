package com.prosos.sosos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "문의") // 한글 테이블명 매핑
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "문의ID") // 한글 컬럼명 매핑
    private Long id;

    @Column(name = "사용자ID", nullable = false)
    private Long userId;  // 문의를 남긴 사용자 ID

    @Column(name = "제목", nullable = false)
    private String title;

    @Column(name = "내용", nullable = false)
    private String content;

    @Column(name = "답변")
    private String answer;  // 판매자가 작성한 답변

    @Column(name = "등록일", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "답변작성일")
    private LocalDateTime answeredDate;  // 답변이 작성된 날짜

    @Column(name = "판매자이름")
    private String sellerName; // 판매자 이름 (답변 작성 시 저장)

    // 기본 생성자
    public Inquiry() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getAnsweredDate() { return answeredDate; }
    public void setAnsweredDate(LocalDateTime answeredDate) { this.answeredDate = answeredDate; }

    public String getSellerName() { return sellerName; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }
}
