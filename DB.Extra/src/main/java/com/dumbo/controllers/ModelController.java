package com.dumbo.controllers;

import hibernate.Model;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.dumbo.repositories.ModelRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/models")
public class ModelController {

    @Autowired
    private ModelRepository modelRepository;

    @PostMapping("/")
    public ResponseEntity<Object> Save(@RequestBody Model model) {
        Model savedModel = modelRepository.save(model);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedModel.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public void DeleteById(@PathVariable int id) {
        modelRepository.deleteById(id);
    }

    @DeleteMapping("/")
    public void DeleteAll() {
        modelRepository.deleteAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> Update(@RequestBody Model model, @PathVariable int id) {
        Optional<Model> companyOptional = modelRepository.findById(id);
        if (companyOptional.isEmpty())
            return ResponseEntity.notFound().build();
        model.setId(id);
        modelRepository.save(model);
        return ResponseEntity.noContent().build();
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
