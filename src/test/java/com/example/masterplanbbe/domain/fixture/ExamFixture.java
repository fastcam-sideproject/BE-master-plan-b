package com.example.masterplanbbe.domain.fixture;

import com.example.masterplanbbe.domain.exam.dto.SubjectDto;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.exam.request.ExamCreateRequest;
import com.example.masterplanbbe.domain.exam.request.ExamUpdateRequest;

import java.util.ArrayList;
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
                .subjects(new ArrayList<>())
                .examDetail(ExamDetail.builder().build())
                .build();
    }

    public static ExamCreateRequest createExamCreateRequest(String title) {
        return new ExamCreateRequest(title, IT_ICT, "테스트", NATIONAL_CERTIFIED, List.of(), "준비물", "자격요건", "시험구조", "합격기준");
    }

    public static ExamUpdateRequest createExamUpdateRequest(String title) {
        return new ExamUpdateRequest(title, IT_ICT, "테스트", 3.0, 100, NATIONAL_CERTIFIED, List.of(), "준비물", "자격요건", "시험구조", "합격기준");
    }

    public static ExamUpdateRequest createExamUpdateRequest(String title,
                                                            Exam exam,
                                                            List<String> subjectTitles) {
        List<SubjectDto> subjects = subjectTitles != null ? new ArrayList<>() : null;
        if (subjectTitles != null) {
            for (int i = 0; i < subjectTitles.size(); i++) {
                subjects.add(new SubjectDto(i + 1L, exam.getId(), subjectTitles.get(i), "설명"));
            }
        }
        return new ExamUpdateRequest(title, IT_ICT, "테스트", 3.0, 100, NATIONAL_CERTIFIED, subjects, "준비물", "자격요건", "시험구조", "합격기준");
    }

}
