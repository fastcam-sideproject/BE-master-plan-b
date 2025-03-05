package com.example.masterplanbbe.domain.spec.service;

import com.example.masterplanbbe.common.request.CustomPageRequest;
import com.example.masterplanbbe.common.response.PageResponse;
import com.example.masterplanbbe.domain.spec.dto.SpecItemCardDto;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.spec.enums.SpecSortOption;
import com.example.masterplanbbe.domain.spec.repository.SpecRepositoryPort;
import com.example.masterplanbbe.domain.spec.request.SpecCreateRequest;
import com.example.masterplanbbe.domain.spec.request.SpecUpdateRequest;
import com.example.masterplanbbe.domain.spec.response.CreateSpecResponse;
import com.example.masterplanbbe.domain.spec.response.ReadSpecResponse;
import com.example.masterplanbbe.domain.spec.response.UpdateSpecResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecService {
    private final SpecRepositoryPort specRepositoryPort;

    public PageResponse<SpecItemCardDto> getAllSpec(CustomPageRequest<SpecSortOption> request,
                                                    Long memberId) {
        return new PageResponse<>(specRepositoryPort.getSpecItemCards(request, memberId));
    }

    public ReadSpecResponse getSpec(Long specId) {
        return new ReadSpecResponse(specRepositoryPort.getSpecWithDetails(specId));
    }

    @Transactional
    public CreateSpecResponse create(SpecCreateRequest request) {
        return new CreateSpecResponse(specRepositoryPort.save(request.toSpec()));
    }

    @Transactional
    public void delete(Long specId) {
        specRepositoryPort.deleteById(specId);
    }

    @Transactional
    public UpdateSpecResponse update(Long specId,
                                     SpecUpdateRequest request) {
        Spec spec = specRepositoryPort.getById(specId);
        request.update(spec);
        return new UpdateSpecResponse(spec);
    }
}
