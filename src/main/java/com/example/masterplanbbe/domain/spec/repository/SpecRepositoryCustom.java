package com.example.masterplanbbe.domain.spec.repository;

import com.example.masterplanbbe.common.page.CustomPage;
import com.example.masterplanbbe.common.request.CustomPageRequest;
import com.example.masterplanbbe.domain.spec.dto.SpecItemCardDto;
import com.example.masterplanbbe.domain.spec.dto.SpecWithDetailsDto;
import com.example.masterplanbbe.domain.spec.enums.SpecSortOption;

public interface SpecRepositoryCustom {
    CustomPage<SpecItemCardDto> getSpecItemCards(CustomPageRequest<SpecSortOption> request, Long memberId);
    SpecWithDetailsDto getSpecWithDetails(Long specId);
}
