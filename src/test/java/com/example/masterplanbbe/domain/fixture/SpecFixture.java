package com.example.masterplanbbe.domain.fixture;

import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.exam.entity.Subject;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import com.example.masterplanbbe.domain.exam.request.SubjectCreateRequest;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.spec.request.SpecCreateRequest;
import com.example.masterplanbbe.utils.TestUtils;

import java.util.List;

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

        return spec;
    }

    public static Spec createExistingSpec() {
        return TestUtils.createExistingEntity(SpecFixture::createSpec);
    }

    public static Spec createExistingSpecFrom(Long specId) {
        return TestUtils.createExistingEntity(SpecFixture::createSpec, specId);
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
}
