package com.dumbo.controllers;

import com.dumbo.entities.User;
import com.dumbo.exceptions.AppError;
import com.dumbo.service.CustomUserDetailsService;
import com.dumbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('ROLE_USER')")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @PostMapping("/company/{companyId}")
    public ResponseEntity<Object> SetCompany(@PathVariable Integer companyId, Principal principal) {
        User user = customUserDetailsService.findByUsername(principal.getName()).get();
        try {
            userService.SetCompany(user, companyId);
        } catch (NoSuchElementException exception) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), String.format("Company '%d' doesn't exist", companyId)), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(user);
    }
}
