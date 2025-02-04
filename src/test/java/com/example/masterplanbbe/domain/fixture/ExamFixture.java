package com.example.masterplanbbe.domain.fixture;

import com.example.masterplanbbe.domain.exam.dto.SubjectDto;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.entity.Subject;
import com.example.masterplanbbe.domain.exam.request.ExamCreateRequest;
import com.example.masterplanbbe.domain.exam.request.ExamUpdateRequest;

import java.util.List;
import java.util.stream.Collectors;

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

    public static ExamUpdateRequest createExamUpdateRequest(String title) {
        return new ExamUpdateRequest(title, IT_ICT, "테스트", 3.0, 100, NATIONAL_CERTIFIED, List.of());
    }

    public static ExamUpdateRequest createUpdateRequestWithSubject(String title, Exam exam, List<Subject> subjects) {
        List<SubjectDto> subjectDtoList = subjects.stream()
                .map(SubjectDto::new)
                .toList();
        return new ExamUpdateRequest(title, IT_ICT, "테스트", 3.0, 100, NATIONAL_CERTIFIED, subjectDtoList);
    }

}
