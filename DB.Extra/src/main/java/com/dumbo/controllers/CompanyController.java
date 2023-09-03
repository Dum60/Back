package com.dumbo.controllers;

import hibernate.Company;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.dumbo.repositories.CompanyRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping("/")
    public ResponseEntity<Object> Save(@RequestBody Company company) {
        Company savedCompany = companyRepository.save(company);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCompany.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public void DeleteById(@PathVariable int id) {
        companyRepository.deleteById(id);
    }

    @DeleteMapping("/")
    public void DeleteAll() {
        companyRepository.deleteAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> Update(@RequestBody Company company, @PathVariable int id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isEmpty())
            return ResponseEntity.notFound().build();
        company.setId(id);
        companyRepository.save(company);
        return ResponseEntity.noContent().build();
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
