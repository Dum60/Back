package com.dumbo.controllers;

import com.dumbo.entities.Role;
import com.dumbo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/roles")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/")
    public List<Role> getAll() { return roleRepository.findAll(); }

    @PostMapping("/")
    public ResponseEntity<Object> Save (@RequestBody Role role) {
        Role savedRole = roleRepository.save(role);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedRole.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
