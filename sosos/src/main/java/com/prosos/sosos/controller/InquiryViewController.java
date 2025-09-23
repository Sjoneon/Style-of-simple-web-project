package com.prosos.sosos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inquiries")
public class InquiryViewController {

    @GetMapping
    public String showInquiryPage() {
        return "inquiry"; // inquiry.html로 매핑
    }
}


