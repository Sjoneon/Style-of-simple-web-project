package com.prosos.sosos.controller;

import com.prosos.sosos.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/keywords")
public class KeywordController {

    private final SellerService sellerService;

    @Autowired
    public KeywordController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    // 키워드 추가 엔드포인트
    @PostMapping
    public ResponseEntity<Void> addKeyword(@RequestParam String keyword) {
        sellerService.manageKeyword(keyword, true);
        return ResponseEntity.ok().build();
    }

    // 키워드 삭제 엔드포인트
    @DeleteMapping
    public ResponseEntity<Void> deleteKeyword(@RequestParam String keyword) {
        sellerService.manageKeyword(keyword, false);
        return ResponseEntity.noContent().build();
    }
}
