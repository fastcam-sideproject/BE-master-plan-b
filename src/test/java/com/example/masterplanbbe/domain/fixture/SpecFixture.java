package com.example.masterplanbbe.domain.fixture;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.exam.entity.Subject;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
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
        Spec spec = new Spec(
                "TOEIC",
                "ETS",
                LANGUAGE,
                ETC,
                3.0,
                100,
                null
        );

        ExamDetail examDetail = new ExamDetail(
                spec,
                "준비물",
                "응시자격",
                "시험구조",
                "합격기준",
                null,
                null
        );

        Subject subject1 = new Subject(
                examDetail,
                "LC",
                "듣기 평가"
        );
        Subject subject2 = new Subject(
                examDetail,
                "RC",
                "말하기 평가"
        );

        LocalDate now = LocalDate.now();

        Exam exam = new Exam(
                examDetail,
                now.getYear() + "년 1회",
                3.0,
                0,
                now.minusDays(5L),
                now.plusDays(3L),
                now.plusDays(8L)
        );

        spec.specifyLatestExam(exam);

        return spec;
    }

    public static Spec createExistingSpec() {
        return TestUtils.createExistingEntity(SpecFixture::createSpec);
    }

    public static Spec createExistingSpecFrom(Long specId) {
        return TestUtils.createExistingEntity(SpecFixture::createSpec, specId);
    }

    public static Spec createUpdatedSpec(Supplier<Spec> specSupplier,
                                         CertificationType certificationType) {
        return TestUtils.withSetup(
                specSupplier,
                spec -> ReflectionTestUtils.setField(spec, "certificationType", NATIONAL_CERTIFIED)
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

    public static SpecUpdateRequest createSpecUpdateRequest(Spec spec,
                                                            CertificationType certificationType) {
        return new SpecUpdateRequest(
                spec.getName(),
                spec.getCategory(),
                certificationType,
                spec.getIssuingOrganization(),
                spec.getDifficulty(),
                spec.getParticipantCount()
        );
    }

    public static SpecItemCardDto createSpecItemCardDto(Spec spec,
                                                        boolean isBookmarked) {
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
