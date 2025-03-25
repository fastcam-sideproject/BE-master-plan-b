package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.Spec;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecRepository extends JpaRepository<Spec, Long> {
    Spec findByName(String name);
}
