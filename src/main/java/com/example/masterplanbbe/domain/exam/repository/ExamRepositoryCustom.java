package com.example.masterplanbbe.domain.exam.repository;

import com.example.masterplanbbe.common.page.CustomPage;
import com.example.masterplanbbe.common.request.CustomPageRequest;
import com.example.masterplanbbe.domain.exam.dto.ExamItemCardDto;
import com.example.masterplanbbe.domain.exam.dto.ExamWithDetailsDto;
import com.example.masterplanbbe.domain.exam.enums.ExamSortOption;

public interface ExamRepositoryCustom {
    CustomPage<ExamItemCardDto> getExamItemCards(CustomPageRequest<ExamSortOption> request, String email);
    ExamWithDetailsDto getExamWithDetails(Long examId, String email);
}
