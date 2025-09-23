package com.prosos.sosos.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prosos.sosos.dto.ProductDto;
import com.prosos.sosos.model.Order;
import com.prosos.sosos.model.Product;
import com.prosos.sosos.model.User;
import com.prosos.sosos.repository.OrderRepository;
import com.prosos.sosos.repository.ProductRepository;
import com.prosos.sosos.service.SellerService;
import com.prosos.sosos.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    private final SellerService sellerService;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public ProductController(SellerService sellerService, ObjectMapper objectMapper, UserService userService, ProductRepository productRepository, OrderRepository orderRepository) {
        this.sellerService = sellerService;
        this.objectMapper = objectMapper;
        this.userService = userService;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    // 상품 등록 엔드포인트
    @PostMapping
    public ResponseEntity<Void> addProduct(
            @ModelAttribute ProductDto productDto,
            @RequestParam("image") MultipartFile imageFile,
            @RequestParam(value = "descriptionImage", required = false) MultipartFile descriptionImageFile,
            @RequestParam("keywords") String keywordsJson
    ) throws IOException {
        Map<String, List<String>> keywords = objectMapper.readValue(keywordsJson, new TypeReference<>() {});
        sellerService.addProduct(productDto, imageFile, keywords, descriptionImageFile);

        String redirectUrl = "http://localhost:8085/seller/dashboard";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Location", redirectUrl);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    // 상품 상세 페이지
    @GetMapping("/detail")
    public String getProductDetailPage(@RequestParam("id") Long id, Model model) {
        ProductDto product = sellerService.getProductById(id);
        model.addAttribute("product", product);
        return "product_detail";
    }

    // 모든 상품 반환
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = sellerService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // 상품 수정 엔드포인트
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long productId,
            @ModelAttribute ProductDto productDto,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            @RequestParam(value = "descriptionImage", required = false) MultipartFile descriptionImageFile
    ) {
        ProductDto updatedProduct = sellerService.updateProduct(productId, productDto, imageFile, descriptionImageFile);
        return ResponseEntity.ok(updatedProduct);
    }

    // 상품 삭제 엔드포인트 (DELETE /api/products/delete/{productId})
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        sellerService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    // 상품 검색 엔드포인트
    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProductsByTitle(@RequestParam String title) {
        List<ProductDto> products = sellerService.searchProductsByTitle(title);
        return ResponseEntity.ok(products);
    }

    // 카테고리별 상품 조회
    @GetMapping("/category")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@RequestParam String category) {
        List<ProductDto> products = sellerService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    // 상품 ID로 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        try {
            ProductDto product = sellerService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 상품 수정 페이지 반환
    @GetMapping("/edit-product")
    public String editProductPage(@RequestParam("id") Long id, Model model) {
        ProductDto product = sellerService.getProductById(id);
        model.addAttribute("product", product);
        return "product-edit";
    }


    // 장바구니 페이지
    @GetMapping("/cart")
    public String cartPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/users/login"; // 로그인 페이지로 리다이렉트
        }

        List<ProductDto> cartItems = userService.getCartItems(user);
        model.addAttribute("cartItems", cartItems); // 장바구니 아이템을 모델에 추가
        return "cart"; // templates/cart.html 사용
    }

    

    // 장바구니 데이터 반환
    @GetMapping("/cart/items")
    public ResponseEntity<List<ProductDto>> getCartItems(HttpSession session) {
    User user = (User) session.getAttribute("loggedInUser");
    if (user == null) {
        return ResponseEntity.status(401).body(null); // 로그인되지 않으면 401 반환
    }
    
    // 장바구니 아이템 로깅
    List<ProductDto> cartItems = userService.getCartItems(user);
    System.out.println("장바구니 아이템들: " + cartItems);  // 로그 확인
    return ResponseEntity.ok(cartItems);
    }

    

    // 장바구니에 상품 추가
    @PostMapping("/cart/{productId}")
    public ResponseEntity<?> addToCart(@PathVariable Long productId, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }
    
        // productId를 ProductDto 객체로 변환
        ProductDto productDto = sellerService.getProductById(productId);  // sellerService에서 상품 정보 가져옴
    
        // 수량을 1로 설정
        int quantity = 1;
    
        // 장바구니에 추가
        userService.addToCart(user, productDto, quantity);
    
        return ResponseEntity.ok("장바구니에 추가되었습니다.");
    }
    

    // 장바구니에서 상품 삭제
    @DeleteMapping("/cart/{productId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long productId, HttpSession session) {
    User user = (User) session.getAttribute("loggedInUser");
    if (user == null) {
        return ResponseEntity.status(401).body("로그인이 필요합니다.");
    }
    userService.removeFromCart(user, productId);  // 장바구니에서 상품 삭제
    return ResponseEntity.ok("장바구니에서 제거되었습니다.");
}


    // 구매 처리
    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }
    
        List<ProductDto> cartItems = userService.getCartItems(user);
    
        if (cartItems == null || cartItems.isEmpty()) {
            return ResponseEntity.status(400).body("장바구니가 비어 있습니다.");
        }
    
        try {
            for (ProductDto productDto : cartItems) {
                Product product = productRepository.findById(productDto.getId()).orElseThrow();
            
                if (product.getQuantity() > 0) {
                    product.setQuantity(product.getQuantity() - 1);
                    productRepository.save(product);
            
                    Order order = new Order();
                    order.setBuyer(user);
                    order.setProduct(product);
                    order.setOrderDate(LocalDateTime.now());
            
                    // 여기에 수정된 상태 설정 코드 추가
                    if (product.getQuantity() == 0) {
                        order.setStatus("품절");
                    } else {
                        order.setStatus("남은 수량: " + product.getQuantity() + "개");
                    }
            
                    order.setTotalAmount(BigDecimal.valueOf(productDto.getPrice()));
                    orderRepository.save(order);
            
                    // 저장된 값 확인
                    Order savedOrder = orderRepository.findById(order.getId()).orElseThrow();
                    System.out.println("DB에 저장된 주문 상태: " + savedOrder.getStatus());
                }
            }
            
    
            userService.clearCart(user);
            return ResponseEntity.ok("구매가 완료되었습니다. 메인 페이지로 이동합니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("구매 처리 중 오류가 발생했습니다.");
        }
    }
}    