package com.prosos.sosos.service;

import com.prosos.sosos.dto.ProductDto;
import com.prosos.sosos.model.Keyword;
import com.prosos.sosos.model.Product;
import com.prosos.sosos.model.Seller;
import com.prosos.sosos.model.User;
import com.prosos.sosos.model.Inquiry;
import com.prosos.sosos.model.Order;
import com.prosos.sosos.repository.KeywordRepository;
import com.prosos.sosos.repository.ProductRepository;
import com.prosos.sosos.repository.SellerRepository;

import jakarta.servlet.http.HttpSession;

import com.prosos.sosos.repository.InquiryRepository;
import com.prosos.sosos.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final InquiryRepository inquiryRepository;
    private final KeywordRepository keywordRepository;

    @Autowired
    public SellerService(SellerRepository sellerRepository, ProductRepository productRepository,
                         OrderRepository orderRepository, InquiryRepository inquiryRepository,
                         KeywordRepository keywordRepository) {
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.inquiryRepository = inquiryRepository;
        this.keywordRepository = keywordRepository;
    }

    // 1.1.1 사업자 등록
    public Seller registerSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    // 1.1.2 사업자 로그인 (사업자등록번호만으로 로그인 가능)
    public boolean login(String businessNumber, String password) {
        Seller seller = sellerRepository.findByBusinessNumber(businessNumber);
        return seller != null && (password.isEmpty() || seller.getPassword().equals(password));
    }

    // 1.1.3 로그아웃
    public void logout(Long sellerId) {
        // 로그아웃 로직: 실제 구현에서는 세션 무효화 또는 JWT 토큰 무효화가 필요할 수 있습니다.
    }

    // 1.2.1 상품 등록 (이미지와 키워드 포함)
    public ProductDto addProduct(ProductDto productDto, MultipartFile imageFile, Map<String, List<String>> keywords, MultipartFile descriptionImageFile) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());
        product.setSituationScore(productDto.getSituationScore());
    
        // 대표 이미지 처리
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImageFile(imageFile);
            product.setImageUrl(imagePath);
        }
    
        // 상세 설명 이미지 처리
        if (descriptionImageFile != null && !descriptionImageFile.isEmpty()) {
            String descriptionImagePath = saveDescriptionImage(descriptionImageFile);
            product.setDescriptionImageUrl(descriptionImagePath); // SQL 필드에 저장
        }
    
        // 판매자 설정
        Seller seller = sellerRepository.findById(productDto.getSellerId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 판매자가 존재하지 않습니다."));
        product.setSeller(seller);
    
        // 상품 저장
        Product savedProduct = productRepository.save(product);
    
        // 키워드 저장
        saveKeywords(savedProduct, keywords);
    
        return new ProductDto(savedProduct);
    }
    
    
    

    // 키워드 저장 메서드
    private void saveKeywords(Product product, Map<String, List<String>> keywords) {
        keywords.forEach((type, keywordList) -> {
            keywordList.forEach(keyword -> {
                // 키워드 가져오거나 새로 생성
                Keyword keywordEntity = keywordRepository.findByKeyword(keyword)
                    .orElseGet(() -> {
                        Keyword newKeyword = new Keyword(keyword, type);
                        return keywordRepository.save(newKeyword);
                    });
    
                // ProductKeyword 생성 및 상품-키워드 관계 추가
                Keyword.ProductKeyword productKeyword = new Keyword.ProductKeyword(product, keywordEntity);
                product.getProductKeywords().add(productKeyword);
            });
        });
    
        // 상품 저장 (상품-키워드 관계 포함)
        productRepository.save(product);
    }
    

    

    // 상품 수정 (이미지와 키워드 포함)
    public ProductDto updateProduct(Long productId, ProductDto productDto, MultipartFile imageFile, MultipartFile descriptionImageFile) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 상품이 존재하지 않습니다."));
    
        product.setName(productDto.getName());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setSituationScore(productDto.getSituationScore());
    
        // 대표 이미지 업데이트 처리
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImageFile(imageFile);
            product.setImageUrl(imagePath);
        }
    
        // 상세 설명 이미지 업데이트 처리
        if (descriptionImageFile != null && !descriptionImageFile.isEmpty()) {
            String descriptionImagePath = saveDescriptionImage(descriptionImageFile);
            product.setDescriptionImageUrl(descriptionImagePath);
        }
    
        Product updatedProduct = productRepository.save(product);
        return new ProductDto(updatedProduct);
    }
    
    


    // 상세 설명 이미지 저장
public String saveDescriptionImage(MultipartFile descriptionImageFile) {
    try {
        String uploadDir = "C:\\Users\\Roneon\\Desktop\\SosProject\\images\\description";
        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs(); // 디렉토리 생성
        }

        // 고유한 파일 이름 생성
        String uniqueFileName = UUID.randomUUID().toString() + "_" + descriptionImageFile.getOriginalFilename();
        String filePath = uploadDir + File.separator + uniqueFileName;
        File destinationFile = new File(filePath);

        // 파일 저장
        descriptionImageFile.transferTo(destinationFile);

        // 브라우저에서 접근 가능한 URL 반환
        return "/images/description/" + uniqueFileName;
    } catch (IOException e) {
        throw new RuntimeException("상세 설명 이미지 저장 실패", e);
    }
}



    // 이미지 저장 로직 (예: 로컬 파일 시스템 또는 클라우드 스토리지)
    private String saveImageFile(MultipartFile imageFile) {
        try {
            String uploadDir = "C:\\Users\\Roneon\\Desktop\\SosProject\\images"; // 이미지 저장 폴더
            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs(); // 디렉토리가 없으면 생성
            }
    
            // 파일 이름을 UUID로 설정하여 고유하게 만듭니다.
            String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            String filePath = uploadDir + File.separator + uniqueFileName;
            File destinationFile = new File(filePath);
    
            // 이미지 파일 저장
            imageFile.transferTo(destinationFile);
    
            // 브라우저에서 접근 가능한 URL 반환
            return "/images/" + uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException("이미지 파일 저장에 실패했습니다.", e);
        }
    }
    
    

    // 1.2.3 상품 삭제
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    // 1.2.4 물품 제목 검색
    public List<ProductDto> searchProductsByTitle(String title) {
        List<Product> products = productRepository.findByNameContaining(title);
        return products.stream().map(ProductDto::new).toList();
    }

    // 1.2.5 전체 상품 목록 조회
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductDto::new).toList();
    }

    // 1.2.6 카테고리 분류
    public List<ProductDto> getProductsByCategory(String categoryName) {
        // 카테고리 필드를 기준으로 검색
        List<Product> products = productRepository.findByCategory(categoryName);
        return products.stream().map(ProductDto::new).toList();
    }
    
    //1.2.7 상세페이지 조회
    public ProductDto getProductById(Long id) {
        System.out.println("상품 조회 중... ID: " + id);
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 ID의 상품이 존재하지 않습니다."));
        System.out.println("조회된 상품: " + product.getName());
        return new ProductDto(product);
    }
    
    
    // 1.3.1 신규 주문 확인 및 처리
    public void processOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 주문이 존재하지 않습니다."));
        order.setStatus("PROCESSED");
        orderRepository.save(order);
    }

    // 1.3.2 취소 관리
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 주문이 존재하지 않습니다."));
        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }

    // 1.3.3 반품 관리
    public void processReturn(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 주문이 존재하지 않습니다."));
        order.setStatus("RETURNED");
        orderRepository.save(order);
    }

    // 1.3.4 교환 관리
    public void processExchange(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 주문이 존재하지 않습니다."));
        order.setStatus("EXCHANGED");
        orderRepository.save(order);
    }


    // 1.3.5 즉시 구매 처리
    public void processPurchase(Long productId, HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        User buyer = (User) session.getAttribute("loggedInUser");
        if (buyer == null) {
            throw new IllegalStateException("로그인된 사용자가 없습니다.");
        }
    
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    
        if (product.getQuantity() <= 0) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
    
        // 상품 수량 감소
        product.setQuantity(product.getQuantity() - 1);
        productRepository.save(product);
    
        // 주문 정보 저장
        Order order = new Order();
        order.setBuyer(buyer);
        order.setProduct(product);
        order.setOrderDate(LocalDateTime.now());
        if (product.getQuantity() == 0) {
            order.setStatus("품절");
        } else {
            order.setStatus("남은 수량: " + product.getQuantity() + "개");
        }
        order.setTotalAmount(BigDecimal.valueOf(product.getPrice()));
        orderRepository.save(order);
    }
    

    // 1.3.6 판매자별 주문 조회
    public List<Order> getOrdersBySeller(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("판매자를 찾을 수 없습니다."));
        return orderRepository.findByProduct_Seller(seller);
    }

    // 1.4.1 문의 답변 등록
    public void answerInquiry(Long inquiryId, String answer) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 문의가 존재하지 않습니다."));
        inquiry.setAnswer(answer);
        inquiry.setSellerName("노란 닉네임"); // 닉네임 설정
        inquiry.setAnsweredDate(LocalDateTime.now());
        inquiryRepository.save(inquiry);
    }


    // 1.4.2 문의 답변 삭제
    public void deleteInquiryAnswer(Long inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 문의가 존재하지 않습니다."));
        inquiry.setAnswer(null);
        inquiryRepository.save(inquiry);
    }

    // 1.4.3 문의 답변 수정
    public void updateInquiryAnswer(Long inquiryId, String newAnswer) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 문의가 존재하지 않습니다."));
        inquiry.setAnswer(newAnswer);
        inquiryRepository.save(inquiry);
    }

    // 1.5.1 키워드 관리
    public void manageKeyword(String keyword, boolean add) {
        if (add) {
            // 새 키워드 생성 및 저장
            Keyword newKeyword = new Keyword(keyword, ""); // 타입 필드에 대한 기본 값 설정
            keywordRepository.save(newKeyword);
        } else {
            // 키워드 삭제
            Optional<Keyword> keywordToDelete = keywordRepository.findByKeyword(keyword);
            if (keywordToDelete.isPresent()) {
                keywordRepository.delete(keywordToDelete.get());
            } else {
                throw new IllegalArgumentException("해당 키워드가 존재하지 않습니다.");
            }
        }
    }


    // 1.6.1 사업자등록번호로 판매자 조회
    public Seller findByBusinessNumber(String businessNumber) {
        return sellerRepository.findByBusinessNumber(businessNumber);
    }
}
