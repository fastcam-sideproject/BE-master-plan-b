package com.example.masterplanbbe.domain.fixture;

import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.utils.TestUtils;

import static com.example.masterplanbbe.domain.exam.enums.Category.*;
import static com.example.masterplanbbe.domain.exam.enums.CertificationType.*;

public class SpecFixture {
    public static Spec createSpec() {
        return Spec.builder()
                .name("정보처리기사")
                .issuingOrganization("한국산업인력공단")
                .category(IT_ICT)
                .certificationType(NATIONAL_CERTIFIED)
                .difficulty(3.0)
                .participantCount(100)
                .build();
    }

    public static Spec createExistingSpec() {
        return TestUtils.createExistingEntity(SpecFixture::createSpec);
    }

    public static Spec createExistingSpecFrom(Long specId) {
        return TestUtils.createExistingEntity(SpecFixture::createSpec, specId);
    }
}
