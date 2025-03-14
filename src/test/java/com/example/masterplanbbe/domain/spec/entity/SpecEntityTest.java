package com.example.masterplanbbe.domain.spec.entity;

import com.example.masterplanbbe.domain.entity.Spec;
import com.example.masterplanbbe.presentation.request.SpecUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.masterplanbbe.domain.enums.CertificationType.*;
import static com.example.masterplanbbe.domain.fixture.SpecFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("스펙 엔티티 테스트")
public class SpecEntityTest {
    @Test
    @DisplayName("update 메서드는 스펙 엔티티의 필드를 수정한다.")
    void update_updates_spec_fields() {
        Spec spec = createExistingSpec();
        SpecUpdateRequest request = createSpecUpdateRequest(spec, NATIONAL_CERTIFIED);

        spec.update(
                request.name(),
                request.issuingOrganization(),
                request.specCategory(),
                request.certificationType(),
                request.participantCount()
        );

        assertAll(
                () -> assertThat(spec.getName()).isEqualTo(request.name()),
                () -> assertThat(spec.getIssuingOrganization()).isEqualTo(request.issuingOrganization()),
                () -> assertThat(spec.getSpecCategory()).isEqualTo(request.specCategory()),
                () -> assertThat(spec.getCertificationType()).isEqualTo(request.certificationType()),
                () -> assertThat(spec.getParticipantCount()).isEqualTo(request.participantCount())
        );
    }
}
