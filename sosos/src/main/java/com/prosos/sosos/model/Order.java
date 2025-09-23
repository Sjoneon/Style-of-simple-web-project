package com.prosos.sosos.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "주문") // MySQL 테이블 이름: "주문"
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "주문ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "사용자ID", nullable = false) // 외래 키: 사용자ID (구매자와 연결)
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "상품ID", nullable = false) // 외래 키: 상품ID (구매한 상품과 연결)
    private Product product;

    @Column(name = "수량", nullable = false)
    private Integer quantity; // 장바구니에 담은 상품의 수량 정보 추가

    @Column(name = "주문날짜", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "주문상태")
    private String status;

    @Column(name = "총결제금액", nullable = false)
    private BigDecimal totalAmount;

    // 기본 생성자
    public Order() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public User getBuyer() {
        return buyer;
    }
    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() { // 수량 Getter
        return quantity;
    }
    public void setQuantity(Integer quantity) { // 수량 Setter
        this.quantity = quantity;
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
