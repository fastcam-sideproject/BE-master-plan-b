package com.example.masterplanbbe.domain.exam.request;

import com.example.masterplanbbe.domain.exam.dto.SubjectDto;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;

import java.time.LocalDate;
import java.util.List;

public record ExamCreateRequest(
        String name,
        Double difficulty,
        Integer participantCount,
        LocalDate applyStartDate,
        LocalDate applyEndDate,
        LocalDate examStartDate,
        ExamDetail examDetail
) {
    public Exam toEntity() {
        return new Exam(
                examDetail,
                name,
                difficulty,
                participantCount,
                applyStartDate,
                applyEndDate,
                examStartDate
        );
    }
}
