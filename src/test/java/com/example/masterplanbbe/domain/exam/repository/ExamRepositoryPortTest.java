package com.example.masterplanbbe.domain.exam.repository;

import com.example.masterplanbbe.domain.repository.ExamRepositoryPort;
import com.example.masterplanbbe.infrastructure.sort.page.CustomPage;
import com.example.masterplanbbe.presentation.request.CustomPageRequest;
import com.example.masterplanbbe.application.dto.ExamItemCardDto;
import com.example.masterplanbbe.application.dto.ExamWithDetailsDto;
import com.example.masterplanbbe.domain.entity.Exam;
import com.example.masterplanbbe.domain.enums.ExamSortOption;
import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.repository.MemberRepository;
import com.example.masterplanbbe.domain.entity.Spec;
import com.example.masterplanbbe.domain.repository.SpecRepository;
import com.example.masterplanbbe.domain.entity.SpecBookmark;
import com.example.masterplanbbe.domain.repository.SpecBookmarkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.masterplanbbe.infrastructure.exception.ErrorCode.*;
import static com.example.masterplanbbe.infrastructure.exception.GlobalException.*;
import static com.example.masterplanbbe.domain.enums.ExamSortOption.START_DATE;
import static com.example.masterplanbbe.domain.fixture.ExamFixture.createExam;
import static com.example.masterplanbbe.domain.fixture.MemberFixture.createMember;
import static com.example.masterplanbbe.domain.fixture.SpecFixture.createSpec;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@Disabled
@SpringBootTest
@DisplayName("시험 리포지토리 테스트")
public class ExamRepositoryPortTest {
    @Autowired private ExamRepositoryPort examRepositoryPort;
    @Autowired private MemberRepository memberRepository;
    @Autowired private SpecBookmarkRepository specBookmarkRepository;
    @Autowired private SpecRepository specRepository;

    @BeforeEach
    void setUp() {
        specBookmarkRepository.deleteAll();
        specRepository.deleteAll();
        examRepositoryPort.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자는 시험을 조회하고 스펙 북마크 여부를 확인한다.")
    void retrieve_exam_and_check_bookmark_status() {
        Member member = memberRepository.save(createMember());
        Spec spec = specRepository.save(createSpec());
        Exam exam1 = createExam(spec.getExamDetails().get(0));
        Exam exam2 = createExam(spec.getExamDetails().get(0));
        examRepositoryPort.saveAll(List.of(exam1, exam2));
        specBookmarkRepository.save(new SpecBookmark(member, spec));
        CustomPageRequest<ExamSortOption> request = new CustomPageRequest<>(0, 25, START_DATE, true);

        CustomPage<ExamItemCardDto> result = examRepositoryPort.getExamItemCards(request, member.getEmail());

        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.content().size()).isEqualTo(2 + 1),
                () -> assertThat(result.content().stream().allMatch(ExamItemCardDto::isBookmarked)).isTrue(),
                () -> assertThat(result.content()).extracting(ExamItemCardDto::name).containsAnyOf(exam1.getName(), exam2.getName())
        );
    }

    @Test
    @DisplayName("사용자는 시험을 상세 조회한다.")
    void retrieve_exam_detail() {
        Member member = memberRepository.save(createMember());
        Spec spec = specRepository.save(createSpec());
        Exam exam = examRepositoryPort.save(createExam(spec.getExamDetails().get(0)));
        specBookmarkRepository.save(new SpecBookmark(member, spec));

        ExamWithDetailsDto result = examRepositoryPort.getExamWithDetails(exam.getId(), member.getEmail());

        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.name()).isEqualTo(exam.getName()),
                () -> assertThat(result.issuingOrganization()).isEqualTo(exam.getExamDetail().getSpec().getIssuingOrganization()),
                () -> assertThat(result.certificationType()).isEqualTo(exam.getExamDetail().getSpec().getCertificationType()),
                () -> assertThat(result.isBookmarked()).isTrue(),
                () -> assertThat(result.preparation()).isEqualTo(exam.getExamDetail().getPreparation()),
                () -> assertThat(result.eligibility()).isEqualTo(exam.getExamDetail().getEligibility()),
                () -> assertThat(result.examStructure()).isEqualTo(exam.getExamDetail().getExamStructure()),
                () -> assertThat(result.passingCriteria()).isEqualTo(exam.getExamDetail().getPassingCriteria())
        );
    }

    @Test
    @DisplayName("존재하지 않는 시험을 조회하면 예외를 발생시킨다.")
    void throw_exception_when_exam_not_found() {
        assertThatThrownBy(() -> examRepositoryPort.getById(-1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(EXAM_NOT_FOUND.getMessage());
    }
}
