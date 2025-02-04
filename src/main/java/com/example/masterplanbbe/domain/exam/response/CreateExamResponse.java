package com.example.masterplanbbe.domain.exam.response;

import com.example.masterplanbbe.domain.exam.dto.SubjectDto;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.enums.Category;

import java.util.List;
import java.util.Objects;

public record CreateExamResponse(
        Long examId,
        String title,
        Category category,
        String authority,
        List<SubjectDto> subjects
) {
    public CreateExamResponse(Exam exam){
        this(
                exam.getId(),
                exam.getTitle(),
                exam.getCategory(),
                exam.getAuthority(),
                Objects.requireNonNull(exam.getSubjects()).stream()
                        .map(SubjectDto::new)
                        .toList()
        );
    }
}
