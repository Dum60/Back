package com.dumbo.repositories;

import hibernate.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {
    List<Model> findAllByCompanyId(Integer companyId);
    List<Model> findAllByName(String name);
}
