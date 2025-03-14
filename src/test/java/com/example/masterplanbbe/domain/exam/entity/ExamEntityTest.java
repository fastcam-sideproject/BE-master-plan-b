package com.example.masterplanbbe.domain.exam.entity;

import com.example.masterplanbbe.domain.exam.request.ExamUpdateRequest;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.masterplanbbe.domain.fixture.ExamFixture.createExamUpdateRequest;
import static com.example.masterplanbbe.domain.fixture.ExamFixture.createExistingExamOf;
import static com.example.masterplanbbe.domain.fixture.SpecFixture.createExistingSpec;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("시험 엔티티 테스트")
public class ExamEntityTest {
    @Test
    @DisplayName("update 메서드는 시험 엔티티의 필드를 수정한다.")
    void update_updates_exam_fields() {
        Spec spec = createExistingSpec();
        Exam exam = createExistingExamOf(spec.getExamDetails().get(0), 1L);
        ExamUpdateRequest request = createExamUpdateRequest(exam, "수정된 이름");

        exam.update(
                request.name(),
                request.participantCount(),
                request.applyStartDate(),
                request.applyEndDate(),
                request.examStartDate()
        );

        assertAll(
                () -> assertThat(exam.getName()).isEqualTo(request.name()),
                () -> assertThat(exam.getParticipantCount()).isEqualTo(request.participantCount()),
                () -> assertThat(exam.getApplyStartDate()).isEqualTo(request.applyStartDate()),
                () -> assertThat(exam.getApplyEndDate()).isEqualTo(request.applyEndDate()),
                () -> assertThat(exam.getExamStartDate()).isEqualTo(request.examStartDate())
        );
    }
}
