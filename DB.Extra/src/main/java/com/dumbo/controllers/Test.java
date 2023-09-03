package com.dumbo.controllers;

import com.dumbo.entities.Role;
import com.dumbo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
public class Test {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/info")
    public String userData(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/roles")
    public List<Role> getAll() { return roleRepository.findAll(); }

    @PostMapping("/roles")
    public ResponseEntity<Object> Save (@RequestBody Role role) {
        Role savedRole = roleRepository.save(role);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedRole.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
