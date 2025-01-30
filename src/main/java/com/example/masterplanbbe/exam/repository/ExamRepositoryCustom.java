package com.example.masterplanbbe.exam.repository;

import com.example.masterplanbbe.exam.dto.ExamItemCardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExamRepositoryCustom {
    Page<ExamItemCardDto> getExamItemCards(Pageable pageable, Long memberId);
}
