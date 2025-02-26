package com.example.masterplanbbe.domain.spec.repository;

import com.example.masterplanbbe.domain.spec.dto.SpecItemCardDto;
import com.example.masterplanbbe.domain.spec.dto.SpecWithDetailsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpecRepositoryCustom {
    Page<SpecItemCardDto> getSpecItemCards(Pageable pageable, Long memberId);
    SpecWithDetailsDto getSpecWithDetails(Long specId);
}
