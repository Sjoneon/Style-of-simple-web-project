package com.prosos.sosos.controller;

import com.prosos.sosos.dto.ProductDto;
import com.prosos.sosos.model.Seller;
import com.prosos.sosos.service.SellerService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    // 사업자 등록 페이지
    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register-seller";
    }

    @PostMapping("/register")
    public String registerSeller(Seller seller) {
        sellerService.registerSeller(seller);
        return "redirect:/seller/login";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String showLoginPage() {
        return "login-seller";
    }

    @PostMapping("/login")
    public String login(@RequestParam String businessNumber, @RequestParam(required = false) String password, Model model) {
        boolean isLoggedIn = sellerService.login(businessNumber, password != null ? password : "");
        if (isLoggedIn) {
            return "redirect:/seller/dashboard";
        } else {
            model.addAttribute("error", "로그인 실패");
            return "login-seller";
        }
    }

    // 대시보드
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "seller-dashboard";
    }

    // 상품 등록 페이지
    @GetMapping("/products/product_register")
    public String showAddProductPage() {
        return "product_register";
    }

    // 상품 등록 처리
    @PostMapping("/products/product_register")
    public String addProduct(@ModelAttribute ProductDto productDto, @RequestParam("image") MultipartFile imageFile) {
        sellerService.addProduct(productDto, imageFile, null, null);

        return "redirect:/seller/products";
    }

    // 상품 목록 조회 페이지
    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", sellerService.getAllProducts());
        return "list-products";
    }

    // 상품 삭제
    @PostMapping("/products/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        sellerService.deleteProduct(productId);
        return "redirect:/seller/products";
    }

    // 주문 관리 페이지
    @GetMapping("/orders")
    public String showOrdersPage() {
        return "seller-orders";
    }

    @GetMapping("/inquiries")
    public String sellerInquiries(HttpSession session) {
        Seller seller = (Seller) session.getAttribute("loggedInUser");
        if (seller == null) {
            return "redirect:/seller/login";
        }
        return "inquiry";
    }
}
