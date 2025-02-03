package com.example.masterplanbbe.domain.fixture;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import com.example.masterplanbbe.domain.exam.request.ExamCreateRequest;

import java.util.List;

import static com.example.masterplanbbe.domain.exam.enums.Category.IT_ICT;
import static com.example.masterplanbbe.domain.exam.enums.CertificationType.*;

public class ExamFixture {
    public static Exam createExam(String title) {
        return Exam.builder()
                .title(title)
                .category(IT_ICT)
                .authority("테스트")
                .participantCount(100)
                .difficulty(3.0)
                .build();
    }

    public static ExamCreateRequest createExamCreateRequest(String title) {
        return new ExamCreateRequest(title, IT_ICT, "테스트", NATIONAL_CERTIFIED, List.of());
    }
}
