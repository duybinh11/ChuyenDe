package com.example.demo.Filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final String authHeader;

    // Constructor để nhận vào request và token
    public CustomHttpServletRequestWrapper(HttpServletRequest request, String authHeader) {
        super(request); // Gọi constructor của HttpServletRequestWrapper
        this.authHeader = authHeader; // Lưu token trong header
    }

    // Ghi đè phương thức getHeader để trả về header Authorization mới
    @Override
    public String getHeader(String name) {
        if ("Authorization".equalsIgnoreCase(name)) {
            return authHeader; // Trả về token từ cookie
        }
        return super.getHeader(name); // Trả về các header còn lại
    }
}
