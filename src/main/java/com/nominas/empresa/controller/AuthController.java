package com.nominas.empresa.controller;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nominas.empresa.models.AppUser;
import com.nominas.empresa.services.UserService;

@Controller
@RequestMapping("/auth")

public class AuthController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Devuelve el archivo login.html en src/main/resources/templates
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // Devuelve el archivo register.html en src/main/resources/templates
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password) throws Exception {
    	AppUser accountDto = new AppUser();
    	accountDto.setUsername(username);
    	accountDto.setEmail(email);
    	accountDto.setPassword(password);
    	accountDto.setEnabled(true);
    	
        userService.registerNewUserAccount(accountDto);
        System.out.println("Datooooooooooss " + accountDto.getEmail() + " " + accountDto.getUsername() + " " + accountDto.getPassword());
        return "redirect:/empresa/auth/login";
    }
}