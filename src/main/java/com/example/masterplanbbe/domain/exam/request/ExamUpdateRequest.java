package com.example.masterplanbbe.domain.exam.request;

import com.example.masterplanbbe.domain.exam.dto.ExamDetailDto;
import com.example.masterplanbbe.domain.exam.dto.SubjectDto;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;

import java.util.List;

public record ExamUpdateRequest(
        String title,
        Category category,
        String authority,
        Double difficulty,
        Integer participantCount,
        CertificationType certificationType,
        List<SubjectDto> subjects,
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {
}
