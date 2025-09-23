package com.prosos.sosos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import com.prosos.sosos.dto.UserRegistrationRequest;
import com.prosos.sosos.model.Seller;
import com.prosos.sosos.model.User;
import com.prosos.sosos.service.SellerService;
import com.prosos.sosos.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final SellerService sellerService;

    @Autowired
    public UserController(UserService userService, SellerService sellerService) {
        this.userService = userService;
        this.sellerService = sellerService;
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String showLoginPage() {
        return "login-seller"; // 변경: login-seller.html 사용
    }

    // 사용자 정보 조회 페이지
    @GetMapping("/info")
    public String showUserInfoPage() {
        return "user-info";
    }

    // 회원가입 페이지
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register-user";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String registerUser(@RequestBody UserRegistrationRequest request, Model model) {
        try {
            userService.registerUser(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getPhone(),
                request.getAddress()
            );
            return "redirect:/"; // 메인 페이지로 리다이렉트
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register-user"; // 에러 발생 시 회원가입 페이지 다시 표시
        }
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam(required = false) String password, // 비밀번호를 선택적으로 받음
                        Model model,
                        HttpSession session) {
        try {
            if (isBusinessNumber(username)) {
                // 판매자 로그인 처리
                Seller seller = sellerService.findByBusinessNumber(username);
                if (seller != null) {
                    if (password == null || password.isEmpty() || seller.getPassword().equals(password)) {
                        session.setAttribute("loggedInUser", seller);
                        session.setAttribute("userType", "seller");
                        model.addAttribute("message", "판매자로 로그인되었습니다.");
                        return "redirect:/seller/dashboard"; // 판매자 대시보드
                    } else {
                        throw new IllegalArgumentException("판매자 비밀번호가 올바르지 않습니다.");
                    }
                } else {
                    throw new IllegalArgumentException("등록되지 않은 사업자번호입니다.");
                }
            } else if (isEmail(username)) {
                // 사용자 로그인 처리
                boolean isLoggedIn = userService.login(username, password);
                if (isLoggedIn) {
                    User user = userService.findByEmail(username);
                    session.setAttribute("loggedInUser", user);
                    session.setAttribute("userType", "user");
                    model.addAttribute("message", "사용자로 로그인되었습니다.");
                    return "redirect:/"; // 사용자 메인 페이지로 리다이렉트
                } else {
                    throw new IllegalArgumentException("사용자 이메일 또는 비밀번호가 올바르지 않습니다.");
                }
            } else {
                throw new IllegalArgumentException("올바른 아이디 형식을 입력하세요.");
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login-seller"; // 로그인 페이지로 다시 이동
        }
    }
    

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/"; // 메인 페이지(로그인 전)로 리다이렉트
    }

    // 사용자 정보 조회 처리
    @GetMapping("/{userId}")
    public String getUserInfo(@PathVariable Long userId, Model model) {
        User user = userService.findUserById(userId);
        if (user != null) {
            model.addAttribute("user", user);
            return "user-info";
        } else {
            model.addAttribute("error", "사용자를 찾을 수 없습니다.");
            return "user-info";
        }
    }

    // 아이디가 사업자번호인지 확인
    private boolean isBusinessNumber(String username) {
        return username.matches("\\d{3}-\\d{2}-\\d{5}");
    }

    // 아이디가 이메일인지 확인
    private boolean isEmail(String username) {
        return username.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }


    @GetMapping("/inquiries")
    public String userInquiries(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/users/login";
        }
        return "inquiry";
    }

    @GetMapping("/type")
    @ResponseBody
    public String getUserType(HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser instanceof Seller) {
            return "seller";
        } else if (loggedInUser instanceof User) {
            return "user";
        } else {
            return "guest";
        }
    }



}
