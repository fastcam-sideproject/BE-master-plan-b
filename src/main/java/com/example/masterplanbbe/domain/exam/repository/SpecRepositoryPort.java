package com.example.masterplanbbe.domain.exam.repository;

import com.example.masterplanbbe.domain.exam.entity.Spec;

public interface SpecRepositoryPort {
    Spec getById(Long specId);
    Spec save(Spec spec);
    void saveAll(Iterable<Spec> specs);
    void deleteById(Long specId);
    void deleteAll();
}
