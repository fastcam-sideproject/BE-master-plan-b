package com.example.masterplanbbe.domain.service;

import com.example.masterplanbbe.presentation.request.CustomPageRequest;
import com.example.masterplanbbe.presentation.response.PageResponse;
import com.example.masterplanbbe.application.dto.SpecItemCardDto;
import com.example.masterplanbbe.domain.entity.Spec;
import com.example.masterplanbbe.domain.enums.SpecSortOption;
import com.example.masterplanbbe.domain.repository.SpecRepositoryPort;
import com.example.masterplanbbe.presentation.request.SpecCreateRequest;
import com.example.masterplanbbe.presentation.request.SpecUpdateRequest;
import com.example.masterplanbbe.presentation.response.CreateSpecResponse;
import com.example.masterplanbbe.presentation.response.ReadSpecResponse;
import com.example.masterplanbbe.presentation.response.UpdateSpecResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecService {
    private final SpecRepositoryPort specRepositoryPort;

    public PageResponse<SpecItemCardDto> getAllSpec(CustomPageRequest<SpecSortOption> request,
                                                    String email) {
        return new PageResponse<>(specRepositoryPort.getSpecItemCards(request, email));
    }

    public ReadSpecResponse getSpec(Long specId, String email) {
        return new ReadSpecResponse(specRepositoryPort.getSpecWithDetails(specId, email));
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
