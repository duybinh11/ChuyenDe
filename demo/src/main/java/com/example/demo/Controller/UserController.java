package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Request.RequestCreateUser;
import com.example.demo.Request.RequestLogin;
import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("login")
    public String handleLogin(RequestLogin requestLogin, HttpServletResponse response) {
        userService.handleLogin(requestLogin, response);
        return "redirect:/index";
    }

    @GetMapping("register")
    public String register() {
        return "registration";
    }

    @PostMapping("register")
    public String handleRegister(org.springframework.ui.Model model, RequestCreateUser requestCreateUser) {
        userService.createUser(requestCreateUser);
        return "redirect:/login";
    }

    

    @GetMapping("order")
    public String order() {
        return "order";
    }

    @GetMapping("account")
    public String account() {
        return "account";
    }
}
