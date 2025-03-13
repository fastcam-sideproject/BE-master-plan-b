package com.example.masterplanbbe.domain.exam.service;

import com.example.masterplanbbe.common.page.CustomPage;
import com.example.masterplanbbe.common.request.CustomPageRequest;
import com.example.masterplanbbe.common.response.PageResponse;
import com.example.masterplanbbe.domain.exam.dto.ExamItemCardDto;
import com.example.masterplanbbe.domain.exam.dto.ExamWithDetailsDto;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.exam.enums.ExamSortOption;
import com.example.masterplanbbe.domain.exam.repository.ExamRepositoryPort;
import com.example.masterplanbbe.domain.exam.response.ReadExamResponse;
import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.specBookmark.entity.SpecBookmark;
import com.example.masterplanbbe.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.example.masterplanbbe.domain.fixture.ExamFixture.createExistingExamOf;
import static com.example.masterplanbbe.domain.fixture.MemberFixture.createExistingMember;
import static com.example.masterplanbbe.domain.fixture.SpecFixture.createExistingSpec;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ExamServiceTest {
    @InjectMocks
    private ExamService examService;
    @Mock
    private ExamRepositoryPort examRepositoryPort;

    @Test
    @DisplayName("사용자는 시험을 조회하고 스펙 북마크 여부를 확인한다.")
    void retrieve_exam_and_check_bookmark_status() {
        Member member = createExistingMember();
        Spec spec = createExistingSpec();
        CustomPageRequest<ExamSortOption> request = new CustomPageRequest<>(0, 25, null, false);
        CustomPage<ExamItemCardDto> mocked = createMockedExamItemCardPage(spec, member);
        given(examRepositoryPort.getExamItemCards(request, member.getEmail())).willReturn(mocked);

        PageResponse<ExamItemCardDto> result = examService.getAllExam(request, member.getEmail());

        verify(examRepositoryPort, times(1)).getExamItemCards(request, member.getEmail());
        assertExamItemCardPage(spec, result);
    }

    private CustomPage<ExamItemCardDto> createMockedExamItemCardPage(Spec spec,
                                                                     Member member) {
        Exam exam1 = TestUtils.createExistingEntity(spec::getLatestExam, 1L);
        Exam exam2 = createExistingExamOf(spec.getExamDetails().get(0), 2L);
        Exam exam3 = createExistingExamOf(spec.getExamDetails().get(0), 3L);
        SpecBookmark specBookmark = new SpecBookmark(member, spec);

        return new CustomPage<>(0, 25, 2 + 1, List.of(
                new ExamItemCardDto(exam1.getName(), exam1.getDifficulty(), exam1.getExamDetail().getSpec().getCategory(), exam1.getApplyStartDate(), exam1.getExamStartDate(), isBookmarkedBy(spec, specBookmark)),
                new ExamItemCardDto(exam2.getName(), exam2.getDifficulty(), exam2.getExamDetail().getSpec().getCategory(), exam2.getApplyStartDate(), exam2.getExamStartDate(), isBookmarkedBy(spec, specBookmark)),
                new ExamItemCardDto(exam3.getName(), exam3.getDifficulty(), exam3.getExamDetail().getSpec().getCategory(), exam3.getApplyStartDate(), exam3.getExamStartDate(), isBookmarkedBy(spec, specBookmark))
        ));
    }

    private void assertExamItemCardPage(Spec spec, PageResponse<ExamItemCardDto> result) {
        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.content().size()).isEqualTo(2 + 1),
                () -> assertThat(result.content().stream().allMatch(ExamItemCardDto::isBookmarked)).isTrue(),
                () -> assertThat(result.content()).containsExactlyInAnyOrderElementsOf(spec.getExamDetails().get(0).getExams().stream().map(exam -> new ExamItemCardDto(exam.getName(), exam.getDifficulty(), spec.getCategory(), exam.getApplyStartDate(), exam.getExamStartDate(), true)).toList())
        );
    }
    //TODO: 테스트 메서드 본문에 추상화 단계는 거쳐있지만, fixture 내용을 알고 있어야 검증문의 이해가 가능하다.
    // 구체적으로는 spec fixture의 createSpec이 연관 객체를 함께 생성하며, examDetail이 따라서 단 한 개 제공돼있다는 점
    // 이 규칙이 깨지는 순간, 테스트 코드는 더 이상 유효하지 않다.

    private Boolean isBookmarkedBy(Spec spec,
                                   SpecBookmark specBookmark) {
        return spec.getId().equals(specBookmark.getSpec().getId());
    }

    @Test
    @DisplayName("사용자는 시험을 상세 조회한다.")
    void retrieve_exam_detail() {
/*
        Member member = createExistingMember();
        Spec spec = createExistingSpec();
        Exam exam = createExistingExamOf(spec.getExamDetails().get(0), 1L);
        given(examRepositoryPort.getExamWithDetails(exam.getId(), member.getEmail())).willReturn();

        ReadExamResponse result = examService.getExam(exam.getId(), member.getEmail());

        verify(examRepositoryPort, times(1)).getExamWithDetails(exam.getId(), member.getEmail());
        assertExamItemCardDto(exam, result);
*/
    }
}
