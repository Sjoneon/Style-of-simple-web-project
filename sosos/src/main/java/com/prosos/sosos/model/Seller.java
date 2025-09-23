package com.prosos.sosos.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "판매자") // 실제 테이블 이름
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "판매자ID") // 실제 컬럼 이름
    private Long id;

    @Column(name = "사업자등록번호", nullable = false, unique = true)
    private String businessNumber;

    @Column(name = "비밀번호", nullable = false)
    private String password;

    @Column(name = "사업자명", nullable = false)
    private String name;

    @Column(name = "사업자주소")
    private String address;

    @Column(name = "사업자전화번호")
    private String phoneNumber;

    // 판매자가 보유한 상품 목록 (일대다 관계)
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    // 기본 생성자
    public Seller() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBusinessNumber() { return businessNumber; }
    public void setBusinessNumber(String businessNumber) { this.businessNumber = businessNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
}
