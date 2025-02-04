package com.example.masterplanbbe.domain.exam.dto;

import com.example.masterplanbbe.common.annotation.Nullable;
import com.example.masterplanbbe.domain.exam.entity.Subject;

public record SubjectDto(
        @Nullable
        Long id,
        Long examId,
        String title,
        String description
) {
    public SubjectDto(Subject subject) {
        this(subject.getId(), subject.getExam().getId(), subject.getTitle(), subject.getDescription());
    }

    public Subject toEntity() {
        return Subject.builder()
                .title(title)
                .description(description)
                .build();
    }
}
