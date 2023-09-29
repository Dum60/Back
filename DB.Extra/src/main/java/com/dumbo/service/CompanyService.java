package com.dumbo.service;

import com.dumbo.repositories.CompanyRepository;
import hibernate.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Optional<Company> findById(Integer id) {
        return companyRepository.findById(id);
    }
}
