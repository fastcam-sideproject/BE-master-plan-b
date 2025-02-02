package com.example.masterplanbbe.domain.exam.repository;

import com.example.masterplanbbe.domain.exam.dto.ExamItemCardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExamRepositoryCustom {
    Page<ExamItemCardDto> getExamItemCards(Pageable pageable, String memberId);
}
