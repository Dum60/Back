package com.dumbo.controllers;

import com.dumbo.entities.User;
import com.dumbo.exceptions.AppError;
import com.dumbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PostMapping("/users/{id}/roles")
    public ResponseEntity<Object> assignRoles(@PathVariable("id") Integer id, @RequestBody List<String> roles) {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), String.format("User '%d' doesn't exist", id)), HttpStatus.BAD_REQUEST);
        }
        try {
            userService.ChangeUserRoles(roles, user.get());
        } catch (NoSuchElementException exception) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Invalid roles"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(user);
    }
}
