package com.prosos.sosos.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "키워드") // MySQL 테이블명에 한글 사용
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "키워드ID") // 키워드 테이블의 기본 키
    private Long id;

    @Column(name = "키워드", nullable = false) // 키워드 이름
    private String keyword;

    @Column(name = "타입") // "body", "style", "situation"
    private String type;

    // Product와 다대다 관계를 매핑
    @OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductKeyword> productKeywords = new ArrayList<>();

    // 기본 생성자
    public Keyword() {}

    public Keyword(String keyword, String type) {
        this.keyword = keyword;
        this.type = type;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public List<ProductKeyword> getProductKeywords() { return productKeywords; }
    public void setProductKeywords(List<ProductKeyword> productKeywords) {
        this.productKeywords = productKeywords;
    }

    @Entity
    @Table(name = "상품키워드") // ProductKeyword를 내부 클래스(Static Inner Class)로 포함
    public static class ProductKeyword {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "상품키워드ID") // 기본 키
        private Long id;

        @ManyToOne
        @JoinColumn(name = "상품ID", nullable = false) // 상품 테이블과의 외래 키
        private Product product;

        @ManyToOne
        @JoinColumn(name = "키워드ID", nullable = false) // 키워드 테이블과의 외래 키
        private Keyword keyword;

        // 기본 생성자
        public ProductKeyword() {}

        public ProductKeyword(Product product, Keyword keyword) {
            this.product = product;
            this.keyword = keyword;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public Product getProduct() { return product; }
        public void setProduct(Product product) { this.product = product; }

        public Keyword getKeyword() { return keyword; }
        public void setKeyword(Keyword keyword) { this.keyword = keyword; }
    }
}
