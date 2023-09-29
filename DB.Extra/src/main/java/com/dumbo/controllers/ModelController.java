package com.dumbo.controllers;

import com.dumbo.entities.User;
import com.dumbo.exceptions.AppError;
import com.dumbo.service.CustomUserDetailsService;
import hibernate.Model;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.dumbo.repositories.ModelRepository;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/models")
@PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
public class ModelController {

    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/")
    public ResponseEntity<Object> Save(@RequestBody Model model) {
        Model savedModel = modelRepository.save(model);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedModel.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> DeleteById(@PathVariable int id, Principal principal) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(principal.getName());
        User user = customUserDetailsService.findByUsername(principal.getName()).get();
        if (modelRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), "No such model"), HttpStatus.NOT_FOUND);
        }
        if (userDetails.getAuthorities().contains("ROLE_ADMIN") || user.getCompany().getId() == modelRepository.findById(id).get().getCompany().getId()) {
            modelRepository.deleteById(id);
            return ResponseEntity.ok(String.format("Model '%d' deleted", id));
        } else {
            return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(), "Forbidden"), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void DeleteAll() {
        modelRepository.deleteAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> Update(@RequestBody Model model, @PathVariable int id, Principal principal) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(principal.getName());
        User user = customUserDetailsService.findByUsername(principal.getName()).get();
        if (modelRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), "No such model"), HttpStatus.NOT_FOUND);
        }
        if (userDetails.getAuthorities().contains("ROLE_ADMIN") || user.getCompany().getId() == modelRepository.findById(id).get().getCompany().getId()) {
            Optional<Model> modelOptional = modelRepository.findById(id);
            if (modelOptional.isEmpty())
                return ResponseEntity.notFound().build();
            model.setId(id);
            modelRepository.save(model);
            return ResponseEntity.ok(String.format("Model '%d' updated", id));
        } else {
            return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(), "Forbidden"), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/")
    public List<Model> GetAll() {
        return modelRepository.findAll();
    }

    @GetMapping("/{nameOrId}")
    public List<Model> GetByNameOrId(@PathVariable String nameOrId) {
        if (StringUtils.isNumeric(nameOrId)) {
            Optional<Model> company = modelRepository.findById(Integer.parseInt(nameOrId));
            if (company.isEmpty())
                throw new RuntimeException();
            return List.of(company.get());
        }
        return modelRepository.findAllByName(nameOrId);
    }

    @GetMapping("/company/{id}")
    public List<Model> GetByCompanyId(@PathVariable Integer id) {
        return modelRepository.findAllByCompanyId(id);
    }
}
