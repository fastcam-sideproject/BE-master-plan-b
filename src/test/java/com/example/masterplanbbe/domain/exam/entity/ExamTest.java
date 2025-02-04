package com.example.masterplanbbe.domain.exam.entity;

import com.example.masterplanbbe.domain.exam.request.ExamUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.masterplanbbe.domain.fixture.ExamFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@DisplayName("시험 엔티티 테스트")
public class ExamTest {
    @Test
    @DisplayName("update 메서드는 시험 필드를 업데이트한다.")
    void update_method_updates_exam_fields() {
        Exam exam = getExistingExam("기존 시험");
        ExamUpdateRequest request = createExamUpdateRequest("수정된 시험");

        exam.update(request);

        assertAll(
                () -> assertThat(exam.getTitle()).isEqualTo(request.title()),
                () -> assertThat(exam.getCategory()).isEqualTo(request.category()),
                () -> assertThat(exam.getAuthority()).isEqualTo(request.authority()),
                () -> assertThat(exam.getDifficulty()).isEqualTo(request.difficulty()),
                () -> assertThat(exam.getParticipantCount()).isEqualTo(request.participantCount()),
                () -> assertThat(exam.getCertificationType()).isEqualTo(request.certificationType())
        );
    }

    private Exam getExistingExam(String title) {
        Exam exam = createExam(title);
        setField(exam, "id", 1L);
        return exam;
    }

    @Test
    @DisplayName("update 메서드는 요청에 새로운 과목이 포함되어 있으면 과목을 추가한다.")
    void update_method_add_subjects_if_request_has_new_subjects() {
        //TODO: implement this test
    }

    @Test
    @DisplayName("updete 메서드는 요청에 과목 목록이 비어있으면 과목을 제거한다.")
    void update_method_remove_subjects_if_request_has_removed_subjects() {
        //TODO: implement this test
    }

    @Test
    @DisplayName("update 메서드는 요청에 과목 목록이 null 이면 과목을 제거한다.")
    void update_method_handle_null_subjects() {
        //TODO: implement this test
    }
}
