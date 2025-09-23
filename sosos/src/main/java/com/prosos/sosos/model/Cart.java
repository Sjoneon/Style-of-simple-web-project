package com.prosos.sosos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "장바구니")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "장바구니ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "사용자ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "상품ID", nullable = false)
    private Product product;

    @Column(name = "수량", nullable = false)
    private Integer quantity;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
