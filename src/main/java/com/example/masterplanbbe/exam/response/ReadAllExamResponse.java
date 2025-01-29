package com.example.masterplanbbe.exam.response;

import com.example.masterplanbbe.exam.dto.ExamItemCardDto;

import java.util.List;

public record ReadAllExamResponse(
        List<ExamItemCardDto> examItemCardDtoSet
) {}
