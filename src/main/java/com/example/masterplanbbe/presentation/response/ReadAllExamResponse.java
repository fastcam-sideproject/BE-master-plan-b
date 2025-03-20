package com.example.masterplanbbe.presentation.response;

import com.example.masterplanbbe.application.dto.ExamItemCardDto;
import org.springframework.data.domain.Page;

public record ReadAllExamResponse(
        Page<ExamItemCardDto> examItemCardDtoPage
) {}
