package com.prosos.sosos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/") // 루트 경로로 메인 페이지를 매핑
    public String showMainPage() {
        return "main"; // main.html을 반환
    }
}
