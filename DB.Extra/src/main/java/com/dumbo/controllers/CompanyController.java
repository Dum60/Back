package com.dumbo.controllers;

import com.dumbo.entities.User;
import com.dumbo.exceptions.AppError;
import com.dumbo.service.CustomUserDetailsService;
import hibernate.Company;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.dumbo.repositories.CompanyRepository;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
@PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/")
    public ResponseEntity<Object> Save(@RequestBody Company company) {
        Company savedCompany = companyRepository.save(company);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCompany.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> DeleteById(@PathVariable int id, Principal principal) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(principal.getName());
        User user = customUserDetailsService.findByUsername(principal.getName()).get();
        if (companyRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), "No such company"), HttpStatus.NOT_FOUND);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")) || user.getCompany().getId() == id) {
            companyRepository.deleteById(id);
            return ResponseEntity.ok(String.format("Company '%d' deleted", id));
        } else {
            return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(), "Forbidden"), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void DeleteAll() {
        companyRepository.deleteAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> Update(@RequestBody Company company, @PathVariable int id, Principal principal) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(principal.getName());
        User user = customUserDetailsService.findByUsername(principal.getName()).get();
        if (companyRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), "No such company"), HttpStatus.NOT_FOUND);
        }
        if (userDetails.getAuthorities().contains("ROLE_ADMIN") || user.getCompany().getId() == id) {
            Optional<Company> companyOptional = companyRepository.findById(id);
            if (companyOptional.isEmpty())
                return ResponseEntity.notFound().build();
            company.setId(id);
            companyRepository.save(company);
            return ResponseEntity.ok(String.format("Company '%d' updated", id));
        } else {
            return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(), "Forbidden"), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/")
    public List<Company> GetAll() {
        return companyRepository.findAll();
    }

    @GetMapping("/{nameOrId}")
    public List<Company> GetByNameOrId(@PathVariable String nameOrId) {
        if (StringUtils.isNumeric(nameOrId)) {
            Optional<Company> company = companyRepository.findById(Integer.parseInt(nameOrId));
            if (company.isEmpty())
                throw new RuntimeException();
            return List.of(company.get());
        }
        return companyRepository.findAllByName(nameOrId);
    }
}
