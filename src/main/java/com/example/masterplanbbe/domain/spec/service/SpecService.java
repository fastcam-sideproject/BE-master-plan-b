package com.example.masterplanbbe.domain.spec.service;

import com.example.masterplanbbe.domain.spec.dto.SpecItemCardDto;
import com.example.masterplanbbe.domain.spec.repository.SpecRepositoryPort;
import com.example.masterplanbbe.domain.spec.request.SpecCreateRequest;
import com.example.masterplanbbe.domain.spec.request.SpecUpdateRequest;
import com.example.masterplanbbe.domain.spec.response.CreateSpecResponse;
import com.example.masterplanbbe.domain.spec.response.ReadSpecResponse;
import com.example.masterplanbbe.domain.spec.response.UpdateSpecResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecService {
    private final SpecRepositoryPort specRepositoryPort;

    public Page<SpecItemCardDto> getAllSpec(Pageable pageable,
                                            String memberId) {
//        return specRepositoryPort.getSpecItemCards(pageable, memberId);
        return null;
    }

    public ReadSpecResponse getSpec(Long specId) {
//        return new ReadSpecResponse(specRepositoryPort.getSpecWithDetails(specId));
        return null;
    }

    public CreateSpecResponse create(SpecCreateRequest request) {
        return null;
    }

    public void delete(Long specId) {
        specRepositoryPort.deleteById(specId);
    }

    @Transactional
    public UpdateSpecResponse update(Long specId,
                                     SpecUpdateRequest request) {
//        Spec spec = specRepositoryPort.getById(specId);
//        spec.update(request);
//        return new UpdateSpecResponse(spec);
        return null;
    }
}
