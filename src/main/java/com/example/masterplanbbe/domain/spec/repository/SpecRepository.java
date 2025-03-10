package com.example.masterplanbbe.domain.spec.repository;

import com.example.masterplanbbe.domain.spec.entity.Spec;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecRepository extends JpaRepository<Spec, Long> {
}
