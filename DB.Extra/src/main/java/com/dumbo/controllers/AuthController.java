package com.dumbo.controllers;

import com.dumbo.dtos.RegistrationUserDto;
import com.dumbo.dtos.JwtRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.dumbo.service.AuthService;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

//    @GetMapping("/registration")
//    public String registrationPage(Model model) {
//        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
//        model.addAttribute("registrationUserDto", registrationUserDto);
//        return "registration";
//    }
//
//    @GetMapping("/auth")
//    public String authPage(Model model) {
//        JwtRequest jwtRequest = new JwtRequest();
//        model.addAttribute("jwtRequest", jwtRequest);
//        return "auth";
//    }

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return authService.registerNewUser(registrationUserDto);
    }
}
