package com.example.masterplanbbe.domain.spec.service;

import com.example.masterplanbbe.common.page.CustomPage;
import com.example.masterplanbbe.common.request.CustomPageRequest;
import com.example.masterplanbbe.common.response.PageResponse;
import com.example.masterplanbbe.domain.fixture.MemberFixture;
import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.spec.dto.SpecItemCardDto;
import com.example.masterplanbbe.domain.spec.dto.SpecWithDetailsDto;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.spec.enums.SpecSortOption;
import com.example.masterplanbbe.domain.spec.repository.SpecRepositoryPort;
import com.example.masterplanbbe.domain.spec.request.SpecCreateRequest;
import com.example.masterplanbbe.domain.spec.request.SpecUpdateRequest;
import com.example.masterplanbbe.domain.spec.response.CreateSpecResponse;
import com.example.masterplanbbe.domain.spec.response.ReadSpecResponse;
import com.example.masterplanbbe.domain.spec.response.UpdateSpecResponse;
import com.example.masterplanbbe.domain.specBookmark.entity.SpecBookmark;
import com.example.masterplanbbe.utils.TestUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.example.masterplanbbe.domain.fixture.MemberFixture.*;
import static com.example.masterplanbbe.domain.fixture.SpecBookmarkFixture.createSpecBookmark;
import static com.example.masterplanbbe.domain.fixture.SpecFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("스펙 서비스 테스트")
public class SpecServiceTest {
    @InjectMocks
    SpecService specService;
    @Mock
    SpecRepositoryPort specRepositoryPort;

    @Test
    @DisplayName("사용자는 스펙을 조회하고 북마크 여부를 확인한다.")
    void get_spec_and_check_bookmark() {
        Member member = createMember();
        CustomPageRequest<SpecSortOption> request = new CustomPageRequest<>(0, 25, null, false);
        CustomPage<SpecItemCardDto> mocked = createMockedSpecItemCardPage(member);
        given(specRepositoryPort.getSpecItemCards(request, member.getId())).willReturn(mocked);

        PageResponse<SpecItemCardDto> result = specService.getAllSpec(request, member.getId());

        verify(specRepositoryPort, times(1)).getSpecItemCards(request, member.getId());
        assertSpecItemCardPage(result);
    }

    private CustomPage<SpecItemCardDto> createMockedSpecItemCardPage(Member member) {
        Spec spec1 = createExistingSpecFrom(1L);
        Spec spec2 = createExistingSpecFrom(2L);
        SpecBookmark specBookmark1 = createSpecBookmark(member, spec1);

        return new CustomPage<>(0, 25, 2, List.of(
                new SpecItemCardDto(spec1, null, isBookmarkedBy(spec1, specBookmark1)),
                new SpecItemCardDto(spec2, null, isBookmarkedBy(spec2, specBookmark1))
        ));
    }

    private void assertSpecItemCardPage(PageResponse<SpecItemCardDto> result) {
        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.content().size()).isEqualTo(2),
                () -> assertThat(result.content().get(0).isBookmarked()).isTrue(),
                () -> assertThat(result.content().get(1).isBookmarked()).isFalse()
        );
    }

    private Boolean isBookmarkedBy(Spec spec,
                                   SpecBookmark specBookmark) {
        return spec.getId().equals(specBookmark.getSpec().getId());
    }

    @Test
    @DisplayName("사용자는 스펙 상세 정보를 조회한다.")
    void get_spec_detail() {
        Long specId = 1L;
        Spec spec = createExistingSpecFrom(specId);
        SpecWithDetailsDto mocked = new SpecWithDetailsDto(spec, spec.getExamDetails().get(0), false);
        given(specRepositoryPort.getSpecWithDetails(spec.getId())).willReturn(mocked);

        ReadSpecResponse result = specService.getSpec(specId);

        verify(specRepositoryPort, times(1)).getSpecWithDetails(specId);
        assertAll(
                () -> assertThat(result.name()).isEqualTo(spec.getName()),
                () -> assertThat(result.issuingOrganization()).isEqualTo(spec.getIssuingOrganization()),
                () -> assertThat(result.certificationType()).isEqualTo(spec.getCertificationType()),
                () -> assertThat(result.preparation()).isEqualTo(spec.getExamDetails().get(0).getPreparation()),
                () -> assertThat(result.eligibility()).isEqualTo(spec.getExamDetails().get(0).getEligibility()),
                () -> assertThat(result.examStructure()).isEqualTo(spec.getExamDetails().get(0).getExamStructure()),
                () -> assertThat(result.passingCriteria()).isEqualTo(spec.getExamDetails().get(0).getPassingCriteria())
        );
    }

    @Test
    @DisplayName("관리자는 스펙을 추가한다.")
    void create_spec() {
        SpecCreateRequest request = createSpecCreateRequest();
        given(specRepositoryPort.save(any(Spec.class))).willAnswer(TestUtils::simulateSavingEntity);

        CreateSpecResponse result = specService.create(request);

        verify(specRepositoryPort, times(1)).save(any(Spec.class));
        assertAll(
                () -> assertThat(result.name()).isEqualTo(request.name()),
                () -> assertThat(result.issuingOrganization()).isEqualTo(request.issuingOrganization()),
                () -> assertThat(result.certificationType()).isEqualTo(request.certificationType()),
                () -> assertThat(result.preparation()).isEqualTo(request.preparation()),
                () -> assertThat(result.eligibility()).isEqualTo(request.eligibility()),
                () -> assertThat(result.examStructure()).isEqualTo(request.examStructure()),
                () -> assertThat(result.passingCriteria()).isEqualTo(request.passingCriteria())
        );
    }

    @Test
    @DisplayName("관리자는 스펙을 수정한다.")
    void update_spec() {
        Long specId = 1L;
        Spec spec = createExistingSpecFrom(specId);
        SpecUpdateRequest request = createSpecUpdateRequest(spec.getExamDetails());
        given(specRepositoryPort.getById(any(Long.class))).willReturn(spec);

        UpdateSpecResponse result = specService.update(specId, request);

        verify(specRepositoryPort, times(1)).getById(any(Long.class));
        assertAll(
                () -> assertThat(result.name()).isEqualTo(request.name()),
                () -> assertThat(result.issuingOrganization()).isEqualTo(request.issuingOrganization()),
                () -> assertThat(result.certificationType()).isEqualTo(request.certificationType()),
                () -> assertThat(result.difficulty()).isEqualTo(request.difficulty()),
                () -> assertThat(result.participantCount()).isEqualTo(request.participantCount()),
                () -> assertThat(result.examDetails().size()).isEqualTo(request.examDetails().size()),
                () -> assertThat(result.examDetails().get(0).getPreparation()).isEqualTo(request.examDetails().get(0).getPreparation()),
                () -> assertThat(result.examDetails().get(0).getEligibility()).isEqualTo(request.examDetails().get(0).getEligibility()),
                () -> assertThat(result.examDetails().get(0).getExamStructure()).isEqualTo(request.examDetails().get(0).getExamStructure()),
                () -> assertThat(result.examDetails().get(0).getPassingCriteria()).isEqualTo(request.examDetails().get(0).getPassingCriteria())
        );
    }

    @Test
    @DisplayName("관리자는 스펙을 삭제한다.")
    void delete_spec() {
        Long specId = 1L;
        willDoNothing().given(specRepositoryPort).deleteById(any(Long.class));

        specService.delete(specId);

        verify(specRepositoryPort, times(1)).deleteById(any(Long.class));
    }
}
