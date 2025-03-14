package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.infrastructure.sort.page.CustomPage;
import com.example.masterplanbbe.presentation.request.CustomPageRequest;
import com.example.masterplanbbe.application.dto.ExamItemCardDto;
import com.example.masterplanbbe.application.dto.ExamWithDetailsDto;
import com.example.masterplanbbe.domain.enums.ExamSortOption;

public interface ExamRepositoryCustom {
    CustomPage<ExamItemCardDto> getExamItemCards(CustomPageRequest<ExamSortOption> request, String email);
    ExamWithDetailsDto getExamWithDetails(Long examId, String email);
}
