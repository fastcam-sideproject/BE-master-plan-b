package com.example.masterplanbbe.exam.response;

import com.example.masterplanbbe.exam.dto.ExamItemCardDto;
import org.springframework.data.domain.Page;

public record ReadAllExamResponse(
        Page<ExamItemCardDto> examItemCardDtoPage
) {}
