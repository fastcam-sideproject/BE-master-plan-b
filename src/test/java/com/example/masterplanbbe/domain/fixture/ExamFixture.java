package com.example.masterplanbbe.domain.fixture;

import com.example.masterplanbbe.domain.exam.dto.SubjectDto;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.exam.request.ExamCreateRequest;
import com.example.masterplanbbe.domain.exam.request.ExamUpdateRequest;
import com.example.masterplanbbe.utils.TestUtils;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.example.masterplanbbe.domain.exam.enums.Category.IT_ICT;
import static com.example.masterplanbbe.domain.exam.enums.CertificationType.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class ExamFixture {
    public static Exam createExam(Long id) {
        Exam exam = Exam.builder()
                .title("title")
                .category(IT_ICT)
                .authority("테스트")
                .participantCount(100)
                .certificationType(NATIONAL_CERTIFIED)
                .difficulty(3.0)
                .subjects(new ArrayList<>())
                .build();
        setField(exam, "id", id);

        return exam;
    }

    public static Exam createExam(String title) {
        Supplier<Exam> examBuilder = () -> Exam.builder()
                .title(title)
                .category(IT_ICT)
                .authority("테스트")
                .participantCount(100)
                .certificationType(NATIONAL_CERTIFIED)
                .difficulty(3.0)
                .subjects(new ArrayList<>())
                .build();

        return TestUtils.withSetup(examBuilder, instance -> {
                    setField(instance, "examDetail", ExamDetail.builder()
                            .exam(instance)
                            .preparation("준비물")
                            .eligibility("자격요건")
                            .examStructure("시험구조")
                            .passingCriteria("합격기준")
                            .build());
                }
        );
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
