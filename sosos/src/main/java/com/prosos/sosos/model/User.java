package com.prosos.sosos.model;

import java.util.List;

import jakarta.persistence.*; // javax.persistence 대신 jakarta.persistence로 수정

@Entity
@Table(name = "사용자") // MySQL의 사용자 테이블과 매핑
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "사용자ID")
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts;
    
    @Column(name = "이메일주소", nullable = false, unique = true)
    private String email;

    @Column(name = "비밀번호", nullable = false)
    private String password;

    @Column(name = "사용자이름", nullable = false)
    private String name;

    @Column(name = "휴대폰번호", nullable = false)
    private String phone;

    @Column(name = "주소", nullable = false)
    private String address;

    // 기본 생성자
    public User() {}

    // 모든 필드를 받는 생성자
    public User(String email, String password, String name, String phone, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
