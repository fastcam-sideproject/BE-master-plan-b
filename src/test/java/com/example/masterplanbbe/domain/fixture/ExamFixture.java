package com.example.masterplanbbe.domain.fixture;

import com.example.masterplanbbe.application.dto.ExamItemCardDto;
import com.example.masterplanbbe.application.dto.ExamWithDetailsDto;
import com.example.masterplanbbe.domain.entity.Exam;
import com.example.masterplanbbe.domain.entity.ExamDetail;
import com.example.masterplanbbe.presentation.request.ExamCreateRequest;
import com.example.masterplanbbe.presentation.request.ExamUpdateRequest;
import com.example.masterplanbbe.utils.TestUtils;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.function.Supplier;

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

    public static ExamCreateRequest createExamCreateRequest(ExamDetail examDetail) {
        return new ExamCreateRequest(
                examDetail.getSpec().getName() + " 1회 시험",
                100,
                LocalDate.now().minusDays(7),
                LocalDate.now().minusDays(3),
                LocalDate.now().plusDays(2),
                examDetail.getId()
        );
    }

    public static ExamUpdateRequest createExamUpdateRequest(Exam exam, String name) {
        return new ExamUpdateRequest(
                name,
                exam.getParticipantCount(),
                exam.getApplyStartDate(),
                exam.getApplyEndDate(),
                exam.getExamStartDate()
        );
    }

    public static ExamWithDetailsDto createExamWithDetailsDto(Exam exam) {
        return new ExamWithDetailsDto(
                exam.getName(),
                exam.getExamDetail().getSpec().getIssuingOrganization(),
                exam.getExamDetail().getSpec().getCertificationType(),
                false,
                exam.getExamDetail().getPreparation(),
                exam.getExamDetail().getEligibility(),
                exam.getExamDetail().getExamStructure(),
                exam.getExamDetail().getPassingCriteria()
        );
    }

    public static Exam createUpdatedExam(Supplier<Exam> examSupplier, String name) {
        return TestUtils.withSetup(examSupplier,
                exam -> ReflectionTestUtils.setField(exam, "name", name)
        );
    }

    public static ExamItemCardDto createExamItemCardDto(Exam exam, boolean isBookmarked) {
        return new ExamItemCardDto(
                exam.getName(),
                exam.getExamDetail().getSpec().getSpecCategory(),
                exam.getApplyStartDate(),
                exam.getExamStartDate(),
                isBookmarked
        );
    }
}
