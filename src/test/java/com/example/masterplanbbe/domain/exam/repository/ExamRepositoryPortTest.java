package com.example.masterplanbbe.domain.exam.repository;

import com.example.masterplanbbe.common.page.CustomPage;
import com.example.masterplanbbe.common.request.CustomPageRequest;
import com.example.masterplanbbe.domain.exam.dto.ExamItemCardDto;
import com.example.masterplanbbe.domain.exam.dto.ExamWithDetailsDto;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.exam.enums.ExamSortOption;
import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.member.repository.MemberRepository;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.spec.repository.SpecRepository;
import com.example.masterplanbbe.domain.specBookmark.entity.SpecBookmark;
import com.example.masterplanbbe.domain.specBookmark.repository.SpecBookmarkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.masterplanbbe.domain.exam.enums.ExamSortOption.*;
import static com.example.masterplanbbe.domain.fixture.ExamFixture.createExam;
import static com.example.masterplanbbe.domain.fixture.ExamFixture.createExistingExamOf;
import static com.example.masterplanbbe.domain.fixture.MemberFixture.createMember;
import static com.example.masterplanbbe.domain.fixture.SpecFixture.createExistingSpec;
import static com.example.masterplanbbe.domain.fixture.SpecFixture.createSpec;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
        examRepositoryPort.deleteAll();
        specRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자는 시험을 조회하고 스펙 북마크 여부를 확인한다.")
    void retrieve_exam_and_check_bookmark_status() {
        Member member = memberRepository.save(createMember());
        Spec spec = createSpec();
        Exam exam1 = createExistingExamOf(spec.getExamDetails().get(0), 1L);
        Exam exam2 = createExistingExamOf(spec.getExamDetails().get(0), 2L);
        examRepositoryPort.saveAll(List.of(exam1, exam2));
        specBookmarkRepository.save(new SpecBookmark(member, spec));
        CustomPageRequest<ExamSortOption> request = new CustomPageRequest<>(0, 25, START_DATE, true);

        // TODO: implement this
    }

    @Test
    @DisplayName("사용자는 시험을 상세 조회한다.")
    void retrieve_exam_detail() {
        Member member = memberRepository.save(createMember());
        Spec spec = createSpec();
        Exam exam = createExistingExamOf(spec.getExamDetails().get(0), 1L);
        examRepositoryPort.save(exam);
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
}
