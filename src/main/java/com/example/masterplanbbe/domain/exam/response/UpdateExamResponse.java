package com.example.masterplanbbe.domain.exam.response;

import com.example.masterplanbbe.domain.exam.dto.SubjectDto;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.entity.Subject;
import com.example.masterplanbbe.domain.exam.enums.Category;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record UpdateExamResponse(
        Long examId,
        String title,
        Category category,
        String authority,
        Double difficulty,
        Integer participantCount,
        List<SubjectDto> subjects
) {
    public UpdateExamResponse(Exam exam) {
        this(
                exam.getId(),
                exam.getTitle(),
                exam.getCategory(),
                exam.getAuthority(),
                exam.getDifficulty(),
                exam.getParticipantCount(),
                Objects.requireNonNull(exam.getSubjects())
                        .stream()
                        .map(SubjectDto::new)
                        .toList()
        );
    }
}
