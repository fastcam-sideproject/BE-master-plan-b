package com.example.masterplanbbe.domain.exam.response;

import com.example.masterplanbbe.domain.exam.dto.SubjectDto;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public record UpdateExamResponse(
        String name,
        Integer participantCount,
        LocalDate applyStartDate,
        LocalDate applyEndDate,
        LocalDate examStartDate
) {
    public UpdateExamResponse(Exam exam) {
        this(
                exam.getName(),
                exam.getParticipantCount(),
                exam.getApplyStartDate(),
                exam.getApplyEndDate(),
                exam.getExamStartDate()
        );
    }
}
