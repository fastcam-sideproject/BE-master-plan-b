package com.example.masterplanbbe.domain.exam.response;

import com.example.masterplanbbe.domain.exam.dto.ExamItemCardDto;
import org.springframework.data.domain.Page;

public record ReadAllExamResponse(
        Page<ExamItemCardDto> examItemCardDtoPage
) {}
