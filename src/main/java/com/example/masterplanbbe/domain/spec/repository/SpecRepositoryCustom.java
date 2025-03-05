package com.example.masterplanbbe.domain.spec.repository;

import com.example.masterplanbbe.common.page.CustomPage;
import com.example.masterplanbbe.common.request.CustomPageRequest;
import com.example.masterplanbbe.domain.spec.dto.SpecItemCardDto;
import com.example.masterplanbbe.domain.spec.dto.SpecWithDetailsDto;

public interface SpecRepositoryCustom {
    CustomPage<SpecItemCardDto> getSpecItemCards(CustomPageRequest request, Long memberId);
    SpecWithDetailsDto getSpecWithDetails(Long specId);
}
