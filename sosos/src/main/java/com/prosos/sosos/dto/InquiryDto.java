package com.prosos.sosos.dto;

import java.time.LocalDateTime;

public class InquiryDto {
    private Long id;
    private Long userId;
    private String userName;
    private String title;
    private String content;
    private String answer;
    private LocalDateTime createdDate;
    private LocalDateTime answeredDate;

    // Constructor
    public InquiryDto(Long id, Long userId, String userName, String title, String content,
                      String answer, LocalDateTime createdDate, LocalDateTime answeredDate) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.answer = answer;
        this.createdDate = createdDate;
        this.answeredDate = answeredDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getAnsweredDate() {
        return answeredDate;
    }

    public void setAnsweredDate(LocalDateTime answeredDate) {
        this.answeredDate = answeredDate;
    }
}
