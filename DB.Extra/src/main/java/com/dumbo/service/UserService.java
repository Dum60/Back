package com.dumbo.service;

import com.dumbo.entities.Role;
import com.dumbo.entities.User;
import com.dumbo.repositories.UserRepository;
import hibernate.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CompanyService companyService;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public void ChangeUserRoles (List<String> names, User user) throws NoSuchElementException{
        List<Role> roles = new ArrayList<Role>();
        for (String name : names) {
            Optional<Role> role = roleService.findByName(name);
            if (role.isEmpty()) {
                throw new NoSuchElementException("No such role");
            }
            roles.add(role.get());
        }
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void SetCompany (User user, Integer companyId) throws NoSuchElementException{
        Optional<Company> company = companyService.findById(companyId);
        if (company.isEmpty()) {
            throw new NoSuchElementException("No such company");
        }
        user.setCompany(company.get());
        userRepository.save(user);
    }
}
