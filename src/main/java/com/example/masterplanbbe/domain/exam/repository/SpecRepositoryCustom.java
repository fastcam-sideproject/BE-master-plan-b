package com.example.masterplanbbe.domain.exam.repository;

import org.springframework.data.domain.Pageable;

public interface SpecRepositoryCustom {
    Page<SpecItemCardDto> getSpecItemCards(Pageable pageable, String memberId);
    SpecWithDetailsDto getSpecWithDetails(Long specId);
}
