package com.example.masterplanbbe.domain.spec.repository;

import com.example.masterplanbbe.domain.spec.entity.Spec;

public interface SpecRepositoryPort {
    Spec getById(Long specId);
    Spec save(Spec spec);
    void saveAll(Iterable<Spec> specs);
    void deleteById(Long specId);
    void deleteAll();
}
