package com.example.masterplanbbe.domain.fixture;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.utils.TestUtils;

import java.time.LocalDate;

public class ExamFixture {
    public static Exam createExam(ExamDetail examDetail) {
        return new Exam(
                examDetail,
                "토익 제537회",
                900,
                LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(9)
        );
    }

    public static Exam createExistingExamOf(ExamDetail examDetail, Long examId) {
        return TestUtils.createExistingEntity(() -> ExamFixture.createExam(examDetail), examId);
    }
}
