package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.Spec;

public interface SpecRepositoryPort extends SpecRepositoryCustom {
    Spec getById(Long specId);
    Spec save(Spec spec);
    void saveAll(Iterable<Spec> specs);
    void deleteById(Long specId);
    void deleteAll();
}
