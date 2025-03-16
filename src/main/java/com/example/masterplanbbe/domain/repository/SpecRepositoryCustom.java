package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.infrastructure.sort.page.CustomPage;
import com.example.masterplanbbe.presentation.request.CustomPageRequest;
import com.example.masterplanbbe.application.dto.SpecItemCardDto;
import com.example.masterplanbbe.application.dto.SpecWithDetailsDto;
import com.example.masterplanbbe.domain.enums.SpecSortOption;

public interface SpecRepositoryCustom {
    CustomPage<SpecItemCardDto> getSpecItemCards(CustomPageRequest<SpecSortOption> request, String email);
    SpecWithDetailsDto getSpecWithDetails(Long specId, String email);
}
