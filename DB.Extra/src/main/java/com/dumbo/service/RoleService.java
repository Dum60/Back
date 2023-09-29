package com.dumbo.service;

import com.dumbo.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dumbo.repositories.RoleRepository;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
