package com.example.masterplanbbe.exam.dto;

import com.example.masterplanbbe.exam.entity.Exam;
import com.example.masterplanbbe.exam.enums.Category;

public record ExamItemCardDto (
        String title,
        Category category,
        Double difficulty,
        Integer participants,
        Boolean isBookmarked
) {
    public ExamItemCardDto(Exam exam, Boolean isBookmarked) {
        this(exam.getTitle(), exam.getCategory(), exam.getDifficulty(), exam.getParticipantCount(), isBookmarked);
    }
}
