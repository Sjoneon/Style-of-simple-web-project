package com.prosos.sosos.controller;

import com.prosos.sosos.dto.OrderDto;
import com.prosos.sosos.model.Order;
import com.prosos.sosos.model.User;
import com.prosos.sosos.service.SellerService;
import com.prosos.sosos.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/orders")
public class OrderController {

    private final SellerService sellerService;
    private final UserService userService;

    public OrderController(SellerService sellerService, UserService userService) {
        this.sellerService = sellerService;
        this.userService = userService;
    }

    /**
     * 주문 처리 엔드포인트
     */
    @PutMapping("/{orderId}/process")
    public ResponseEntity<?> processOrder(@PathVariable Long orderId) {
        try {
            sellerService.processOrder(orderId);
            return ResponseEntity.ok(Map.of("message", "주문이 성공적으로 처리되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * 주문 취소 엔드포인트
     */
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        try {
            sellerService.cancelOrder(orderId);
            return ResponseEntity.ok(Map.of("message", "주문이 성공적으로 취소되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * 반품 처리 엔드포인트
     */
    @PutMapping("/{orderId}/return")
    public ResponseEntity<?> processReturn(@PathVariable Long orderId) {
        try {
            sellerService.processReturn(orderId);
            return ResponseEntity.ok(Map.of("message", "반품이 성공적으로 처리되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * 교환 처리 엔드포인트
     */
    @PutMapping("/{orderId}/exchange")
    public ResponseEntity<?> processExchange(@PathVariable Long orderId) {
        try {
            sellerService.processExchange(orderId);
            return ResponseEntity.ok(Map.of("message", "교환이 성공적으로 처리되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * 즉시 구매 처리 엔드포인트
     */
    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseProduct(@RequestParam Long productId, HttpSession session) {
        try {
            sellerService.processPurchase(productId, session);
            return ResponseEntity.ok(Map.of("message", "구매가 완료되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * 판매자별 주문 조회 엔드포인트
     */
    @GetMapping("/seller")
    public ResponseEntity<?> getOrdersForSeller(@RequestParam Long sellerId) {
        try {
            List<Order> orders = sellerService.getOrdersBySeller(sellerId);
            List<OrderDto> orderDtos = orders.stream()
                                             .map(OrderDto::new)
                                             .toList();
            return ResponseEntity.ok(orderDtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // 사용자별 주문 기록 조회
    @GetMapping
    public String getUserOrders(HttpSession session, Model model) {
        // 세션에서 로그인한 사용자 가져오기
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login"; // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
        }

        // 사용자의 주문 기록 조회
        List<OrderDto> orders = userService.getOrdersByUserId(loggedInUser.getId());
        model.addAttribute("orders", orders); // 주문 데이터를 모델에 추가

        return "user-order"; // user-order.html 반환
    }
        
}
