package com.example.masterplanbbe.domain.fixture;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.exam.entity.Subject;
import com.example.masterplanbbe.domain.exam.request.SubjectCreateRequest;
import com.example.masterplanbbe.domain.spec.dto.SpecItemCardDto;
import com.example.masterplanbbe.domain.spec.dto.SpecWithDetailsDto;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.spec.request.SpecCreateRequest;
import com.example.masterplanbbe.domain.spec.request.SpecUpdateRequest;
import com.example.masterplanbbe.utils.TestUtils;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

import static com.example.masterplanbbe.domain.exam.enums.Category.*;
import static com.example.masterplanbbe.domain.exam.enums.CertificationType.*;

public class SpecFixture {
    public static Spec createSpec() {
        Spec spec = Spec.builder()
                .name("TOEIC")
                .issuingOrganization("ETS")
                .category(LANGUAGE)
                .certificationType(ETC)
                .difficulty(3.0)
                .participantCount(100)
                .build();

        ExamDetail examDetail = ExamDetail.builder()
                .spec(spec)
                .preparation("준비물")
                .eligibility("응시자격")
                .examStructure("시험구조")
                .passingCriteria("합격기준")
                .build();

        Subject subject1 = Subject.builder()
                .name("LC")
                .examDetail(examDetail)
                .build();
        Subject subject2 = Subject.builder()
                .name("RC")
                .examDetail(examDetail)
                .build();

        LocalDate now = LocalDate.now();

        Exam exam = Exam.builder()
                .examDetail(examDetail)
                .name(now.getYear() + "년 1회")
                .applyStartDate(now.minusDays(5L))
                .applyEndDate(now.plusDays(3L))
                .examStartDate(now.plusDays(10L))
                .participantCount(0)
                .build();

        spec.specifyLatestExam(exam);

        return spec;
    }

    public static Spec createExistingSpec() {
        return TestUtils.createExistingEntity(SpecFixture::createSpec);
    }

    public static Spec createExistingSpecFrom(Long specId) {
        return TestUtils.createExistingEntity(SpecFixture::createSpec, specId);
    }

    public static Spec createUpdatedSpec(Supplier<Spec> specSupplier, Double difficulty) {
        return TestUtils.withSetup(
                specSupplier,
                spec -> ReflectionTestUtils.setField(spec, "difficulty", difficulty)
        );
    }

    public static SpecCreateRequest createSpecCreateRequest() {
        return new SpecCreateRequest(
                "TOEIC",
                "ETS",
                LANGUAGE,
                ETC,
                "준비물",
                "응시자격",
                "시험구조",
                "합격기준",
                List.of(
                        new SubjectCreateRequest("LC", "듣기 평가"),
                        new SubjectCreateRequest("RC", "말하기 평가")
                )
        );
    }

    public static SpecUpdateRequest createSpecUpdateRequest(Spec spec, Double difficulty) {
        return new SpecUpdateRequest(
                spec.getName(),
                spec.getCategory(),
                spec.getCertificationType(),
                spec.getIssuingOrganization(),
                difficulty,
                spec.getParticipantCount()
        );
    }

    public static SpecItemCardDto createSpecItemCardDto(Spec spec, boolean isBookmarked) {
        return new SpecItemCardDto(
                spec.getName(),
                spec.getCategory(),
                spec.getDifficulty(),
                spec.getParticipantCount(),
                spec.getLatestExam().getApplyStartDate(),
                spec.getLatestExam().getApplyEndDate(),
                spec.getLatestExam().getExamStartDate(),
                isBookmarked
        );
    }

    public static SpecWithDetailsDto createSpecWithDetailsDto(Spec spec) {
        return new SpecWithDetailsDto(
                spec.getName(),
                spec.getIssuingOrganization(),
                spec.getCertificationType(),
                false,
                spec.getLatestExam().getExamDetail().getPreparation(),
                spec.getLatestExam().getExamDetail().getEligibility(),
                spec.getLatestExam().getExamDetail().getExamStructure(),
                spec.getLatestExam().getExamDetail().getPassingCriteria()
        );
    }
}
