package com.prosos.sosos.dto;

import com.prosos.sosos.model.Product;

public class ProductDto {
    private Long id;
    private String name;
    private String category;
    private double price;
    private int quantity;
    private String description;
    private Integer situationScore;
    private String imageUrl;
    private Long sellerId;
    private String descriptionImageUrl; // 상세 설명 이미지 URL 필드 추가

    public ProductDto() {}

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.description = product.getDescription();
        this.situationScore = product.getSituationScore();
        this.imageUrl = product.getImageUrl();
        this.sellerId = (product.getSeller() != null) ? product.getSeller().getId() : null;
        this.descriptionImageUrl = product.getDescriptionImageUrl(); // 필드 매핑 추가
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getSituationScore() { return situationScore; }
    public void setSituationScore(Integer situationScore) { this.situationScore = situationScore; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Long getSellerId() { return sellerId; }
    public void setSellerId(Long sellerId) { this.sellerId = sellerId; }

    public String getDescriptionImageUrl() { return descriptionImageUrl; } // 추가
    public void setDescriptionImageUrl(String descriptionImageUrl) { this.descriptionImageUrl = descriptionImageUrl; } // 추가
}
