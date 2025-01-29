package com.example.masterplanbbe.exam.repository;

import com.example.masterplanbbe.exam.dto.ExamItemCardDto;

import java.util.List;

public interface ExamRepositoryCustom {
    List<ExamItemCardDto> getExamItemCards(Long memberId);
}
