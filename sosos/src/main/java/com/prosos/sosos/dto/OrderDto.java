package com.prosos.sosos.dto;

import com.prosos.sosos.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDto {

    private Long id; // 주문 ID
    private String productName; // 상품명
    private BigDecimal totalAmount; // 총 결제 금액
    private LocalDateTime orderDate; // 주문 날짜
    private String status; // 주문 상태
    private Integer quantity; // 수량

    public OrderDto() {}

    // Order 객체를 기반으로 OrderDto 생성
    public OrderDto(Order order) {
        this.id = order.getId();
        this.productName = order.getProduct().getName();
        this.totalAmount = order.getTotalAmount();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus(); // 상태값 매핑
        this.quantity = order.getProduct().getQuantity(); // 수량 매핑

        // 상태 값 확인 로그
        System.out.println("OrderDto 생성 중 상태값: " + this.status);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
