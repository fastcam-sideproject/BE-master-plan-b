package com.example.masterplanbbe.domain.spec.repository;


import com.example.masterplanbbe.common.exception.GlobalException;
import com.example.masterplanbbe.common.page.CustomPage;
import com.example.masterplanbbe.common.request.CustomPageRequest;
import com.example.masterplanbbe.domain.exam.repository.ExamRepository;
import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.member.repository.MemberRepository;
import com.example.masterplanbbe.domain.spec.dto.SpecItemCardDto;
import com.example.masterplanbbe.domain.spec.dto.SpecWithDetailsDto;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.spec.enums.SpecSortOption;
import com.example.masterplanbbe.domain.specBookmark.repository.SpecBookmarkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.example.masterplanbbe.common.exception.ErrorCode.SPEC_NOT_FOUND;
import static com.example.masterplanbbe.domain.fixture.ExamFixture.*;
import static com.example.masterplanbbe.domain.fixture.MemberFixture.*;
import static com.example.masterplanbbe.domain.fixture.SpecBookmarkFixture.createSpecBookmark;
import static com.example.masterplanbbe.domain.fixture.SpecFixture.createSpec;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@DisplayName("스펙 레포지토리 테스트")
public class SpecRepositoryPortTest {
    @Autowired private SpecRepositoryPort specRepositoryPort;
    @Autowired private MemberRepository memberRepository;
    @Autowired private SpecBookmarkRepository specBookmarkRepository;
    @Autowired private ExamRepository examRepository;

    @BeforeEach
    void setUp() {
        specBookmarkRepository.deleteAll();
        examRepository.deleteAll();
        specRepositoryPort.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자는 스펙을 조회하고 북마크 여부를 확인할 수 있다.")
    void retrieve_spec_and_check_bookmark_status() {
        Member member = memberRepository.save(createMember());
        Spec spec1 = createSpec();
        Spec spec2 = createSpec();
        specRepositoryPort.saveAll(List.of(spec1, spec2));
        examRepository.save(createExam(spec1.getExamDetails().get(0)));
        specBookmarkRepository.save(createSpecBookmark(member, spec1));
        CustomPageRequest<SpecSortOption> request = new CustomPageRequest<>(0, 25, null, false);

        CustomPage<SpecItemCardDto> result = specRepositoryPort.getSpecItemCards(request, member.getId());

        assertThat(result.content().size()).isEqualTo(2);
        assertAll(
                () -> assertThat(result.content().get(0).name()).isEqualTo(spec1.getName()),
                () -> assertThat(result.content().get(1).name()).isEqualTo(spec2.getName()),
                () -> assertThat(result.content().get(0).isBookmarked()).isTrue(),
                () -> assertThat(result.content().get(1).isBookmarked()).isFalse()
        );
    }

    @Test
    @DisplayName("사용자는 스펙의 상세 정보를 조회할 수 있다")
    void retrieve_spec_detail() {
        Spec spec = specRepositoryPort.save(createSpec());

        SpecWithDetailsDto result = specRepositoryPort.getSpecWithDetails(spec.getId());

        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.name()).isEqualTo(spec.getName()),
                () -> assertThat(result.issuingOrganization()).isEqualTo(spec.getIssuingOrganization()),
                () -> assertThat(result.certificationType()).isEqualTo(spec.getCertificationType()),
                () -> assertThat(result.preparation()).isEqualTo(spec.getExamDetails().get(0).getPreparation()),
                () -> assertThat(result.examStructure()).isEqualTo(spec.getExamDetails().get(0).getExamStructure()),
                () -> assertThat(result.eligibility()).isEqualTo(spec.getExamDetails().get(0).getEligibility()),
                () -> assertThat(result.passingCriteria()).isEqualTo(spec.getExamDetails().get(0).getPassingCriteria()),
                () -> assertThat(result.isBookmarked()).isFalse()
        );
    }

    @Test
    @DisplayName("존재하지 않는 스펙을 조회하면 예외를 발생시킨다.")
    void throw_exception_when_exam_not_found() {
        assertThatThrownBy(() -> specRepositoryPort.getById(-1L))
                .isInstanceOf(GlobalException.NotFoundException.class)
                .hasMessageContaining(SPEC_NOT_FOUND.getMessage());
    }
}
