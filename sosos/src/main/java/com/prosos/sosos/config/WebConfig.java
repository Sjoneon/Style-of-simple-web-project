package com.prosos.sosos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /images/** 경로를 C:/Users/Roneon/Desktop/SosProject/images 디렉토리와 매핑
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:C:/Users/Roneon/Desktop/SosProject/images/");
    }
}
