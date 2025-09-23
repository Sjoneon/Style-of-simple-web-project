package com.prosos.sosos.service;

import com.prosos.sosos.model.Cart;
import com.prosos.sosos.model.Order;
import com.prosos.sosos.model.Product;
import com.prosos.sosos.model.User;
import com.prosos.sosos.dto.OrderDto;
import com.prosos.sosos.dto.ProductDto;
import com.prosos.sosos.dto.UserLoginRequest;
import com.prosos.sosos.repository.CartRepository;
import com.prosos.sosos.repository.OrderRepository;
import com.prosos.sosos.repository.ProductRepository;
import com.prosos.sosos.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final Map<User, List<Product>> userCartData = new ConcurrentHashMap<>();


    public UserService(UserRepository userRepository, ProductRepository productRepository, CartRepository cartRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    // 로그인 로직
    public boolean login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        return user != null && user.getPassword().equals(request.getPassword());
    }

    // 사용자 ID로 사용자 찾기
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    // 이메일로 사용자 정보 조회
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 이메일과 비밀번호로 일반 사용자 로그인
    public boolean login(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }

    // 회원가입
    public void registerUser(String name, String email, String password, String phone, String address) {
        // 휴대폰 번호 형식 검증
        if (!phone.matches("010-\\d{3,4}-\\d{4}")) {
            throw new IllegalArgumentException("휴대폰 번호는 '010-XXXX-XXXX' 형식으로 입력해야 합니다.");
        }

        // 이메일 중복 검증
        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 사용자 생성 및 저장
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setAddress(address);

        userRepository.save(user);
    }


    // 장바구니에 상품 추가
    public void addToCart(User user, ProductDto productDto, int quantity) {
        // 수량이 1 이상이어야만 추가
        if (quantity <= 0) {
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        }
    
        // Cart 객체 추가
        Cart cart = cartRepository.findByUserIdAndProductId(user.getId(), productDto.getId());
        if (cart == null) {
            // 장바구니에 해당 상품이 없다면 새로 추가
            cart = new Cart();
            cart.setUser(user);
            cart.setProduct(new Product(productDto));  // ProductDto를 Product로 변환
            cart.setQuantity(quantity);  // 사용자가 선택한 수량을 그대로 설정
        } else {
            // 이미 장바구니에 해당 상품이 있다면 수량을 새로 설정
            cart.setQuantity(quantity);  // 기존 수량을 덮어쓰고 새 수량을 설정
        }
        cartRepository.save(cart); // 장바구니 저장
    }
    

    // 장바구니에서 상품 제거
    public void removeFromCart(User user, Long productId) {
        Cart cart = cartRepository.findByUserIdAndProductId(user.getId(), productId); // 장바구니에서 상품 찾기
        if (cart != null) {
            cartRepository.delete(cart);  // 장바구니에서 해당 상품 삭제
        }
    }
    

    // 사용자별 장바구니 데이터 조회
    public List<ProductDto> getCartItems(User user) {
        List<Cart> carts = cartRepository.findByUserId(user.getId()); // 장바구니 데이터를 찾는다.
        List<ProductDto> productDtos = new ArrayList<>();
        
        if (carts.isEmpty()) {
            System.out.println("장바구니가 비어 있습니다."); // 콘솔에서 로그를 통해 확인
        }
    
        for (Cart cart : carts) {
            ProductDto productDto = new ProductDto(cart.getProduct()); // Cart에서 ProductDto로 변환
            productDto.setQuantity(cart.getQuantity());  // 장바구니에서 가져온 수량을 ProductDto에 설정
            productDtos.add(productDto);
        }
        return productDtos;
    }
    

    // 구매 처리
    public void checkout(User user) {
        List<Product> cart = userCartData.get(user);
        if (cart == null || cart.isEmpty()) {
            throw new IllegalStateException("장바구니가 비어 있습니다.");
        }
        userCartData.remove(user); // 구매 완료 후 장바구니 초기화
    }

    public void clearCart(User user) {
        // 사용자 장바구니에서 모든 항목을 삭제
        List<Cart> userCarts = cartRepository.findByUserId(user.getId());
        cartRepository.deleteAll(userCarts);
    }

    // 사용자별 주문 기록 조회
    public List<OrderDto> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByBuyerId(userId);
        if (orders.isEmpty()) {
            System.out.println("주문 기록이 없습니다.");
        } else {
            System.out.println("주문 기록: " + orders);
        }
    
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            OrderDto orderDto = new OrderDto(order);
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }
    
    
}
