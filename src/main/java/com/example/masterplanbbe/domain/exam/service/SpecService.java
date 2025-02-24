package com.example.masterplanbbe.domain.exam.service;

import com.example.masterplanbbe.domain.exam.entity.Spec;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecService {
    private final SpecRepositoryPort specRepositoryPort;

    public Page<SpecItemCardDto> getAllSpec(Pageable pageable,
                                            String memberId) {
        return specRepositoryPort.getSpecItemCards(pageable, memberId);
    }

    public ReadSpecResponse getSpec(Long specId) {
        return new ReadSpecResponse(specRepositoryPort.getSpecWithDetails(specId));
    }

    public CreateSpecResponse create(SpecCreateRequest request) {
        return new CreateSpecResponse(specRepositoryPort.save(request.toEntity()));
    }

    public void delete(Long specId) {
        specRepositoryPort.deleteById(specId);
    }

    @Transactional
    public UpdateSpecResponse update(Long specId,
                                     SpecUpdateRequest request) {
        Spec spec = specRepositoryPort.getById(specId);
        spec.update(request);
        return new UpdateSpecResponse(spec);
    }
}
