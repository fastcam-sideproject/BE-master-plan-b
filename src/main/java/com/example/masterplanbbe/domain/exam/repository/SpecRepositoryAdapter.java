package com.example.masterplanbbe.domain.exam.repository;

import com.example.masterplanbbe.domain.exam.entity.Spec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SpecRepositoryAdapter implements SpecRepositoryPort, SpecRepositoryCustom {
    private final SpecRepository specRepository;

    @Override
    public Page<SpecItemCardDto> getSpecItemCards(Pageable pageable,
                                                  String memberId) {
        return null;
    }

    @Override
    public SpecWithDetailsDto getSpecWithDetails(Long specId) {
        return null;
    }

    @Override
    public Spec getById(Long specId) {
        return specRepository.findById(specId).orElseThrow();
    }

    @Override
    public Spec save(Spec spec) {
        return specRepository.save(spec);
    }

    @Override
    public void saveAll(Iterable<Spec> specs) {
        specRepository.saveAll(specs);
    }

    @Override
    public void deleteById(Long specId) {
        specRepository.deleteById(specId);
    }

    @Override
    public void deleteAll() {
        specRepository.deleteAll();
    }

}
