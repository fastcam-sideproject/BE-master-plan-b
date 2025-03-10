package com.example.masterplanbbe.domain.exam.response;

import com.example.masterplanbbe.domain.exam.dto.ExamWithDetailsDto;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;

public record ReadExamResponse(

) {
    public ReadExamResponse(ExamWithDetailsDto dto) {
        this(

        );
    }
}
