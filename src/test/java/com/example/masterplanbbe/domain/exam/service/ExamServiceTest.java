package com.example.masterplanbbe.domain.exam.service;

import com.example.masterplanbbe.domain.exam.dto.ExamItemCardDto;
import com.example.masterplanbbe.domain.exam.dto.ExamWithDetailsDto;
import com.example.masterplanbbe.domain.exam.dto.SubjectDto;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.entity.ExamBookmark;
import com.example.masterplanbbe.domain.exam.repository.ExamRepositoryPort;
import com.example.masterplanbbe.domain.exam.request.ExamCreateRequest;
import com.example.masterplanbbe.domain.exam.request.ExamUpdateRequest;
import com.example.masterplanbbe.domain.exam.response.CreateExamResponse;
import com.example.masterplanbbe.domain.exam.response.ReadExamResponse;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.masterplanbbe.domain.fixture.ExamFixture.*;
import static com.example.masterplanbbe.domain.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.util.ReflectionTestUtils.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("시험 서비스 테스트")
public class ExamServiceTest {
    @InjectMocks
    ExamService examService;
    @Mock
    ExamRepositoryPort examRepositoryPort;

    @Test
    @DisplayName("사용자는 시험을 조회하고 북마크 여부를 확인한다.")
    void get_all_exam() {
        Member member = createMember();
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<ExamItemCardDto> mocked = createMockedExamItemCardPage(member);
        given(examRepositoryPort.getExamItemCards(any(Pageable.class), any(String.class))).willReturn(mocked);

        examService.getAllExam(pageRequest, "userId");

        verify(examRepositoryPort, times(1)).getExamItemCards(any(Pageable.class), any(String.class));
        assertExamItemCardPage(mocked);
    }

    private Page<ExamItemCardDto> createMockedExamItemCardPage(Member member) {
        Exam exam1 = createExam("exam1");
        Exam exam2 = createExam("exam2");
        ExamBookmark examBookmark = new ExamBookmark(member, exam1);
        return new PageImpl<>(List.of(
                new ExamItemCardDto(exam1, isBookmarked(examBookmark, exam1)),
                new ExamItemCardDto(exam2, isBookmarked(examBookmark, exam2))
        ));
    }

    private void assertExamItemCardPage(Page<ExamItemCardDto> result) {
        assertThat(result.getContent()).hasSize(2);
        assertAll(
                () -> assertThat(result.getContent().get(0).title()).isEqualTo("exam1"),
                () -> assertThat(result.getContent().get(0).isBookmarked()).isTrue(),
                () -> assertThat(result.getContent().get(1).title()).isEqualTo("exam2"),
                () -> assertThat(result.getContent().get(1).isBookmarked()).isFalse()
        );
    }

    private boolean isBookmarked(ExamBookmark examBookmark,
                                 Exam exam) {
        return examBookmark.getExam() == exam;
    }

    @Test
    @DisplayName("사용자는 시험 상세 정보를 조회한다.")
    void get_exam() {
        Long examId = 1L;
        Exam exam = getExistingExamById(examId);
        ExamWithDetailsDto mocked = new ExamWithDetailsDto(exam);
        given(examRepositoryPort.getExamWithDetails(any(Long.class))).willReturn(mocked);

        ReadExamResponse result = examService.getExam(examId);

        verify(examRepositoryPort, times(1)).getExamWithDetails(any(Long.class));
        assertAll(
                () -> assertThat(result.title()).isEqualTo(exam.getTitle()),
                () -> assertThat(result.category()).isEqualTo(exam.getCategory().name()),
                () -> assertThat(result.authority()).isEqualTo(exam.getAuthority()),
                () -> assertThat(result.difficulty()).isEqualTo(exam.getDifficulty()),
                () -> assertThat(result.participantCount()).isEqualTo(exam.getParticipantCount()),
                () -> assertThat(result.certificationType()).isEqualTo(exam.getCertificationType().name()),
                () -> assertThat(result.preparation()).isEqualTo(exam.getExamDetail().getPreparation()),
                () -> assertThat(result.eligibility()).isEqualTo(exam.getExamDetail().getEligibility()),
                () -> assertThat(result.examStructure()).isEqualTo(exam.getExamDetail().getExamStructure()),
                () -> assertThat(result.passingCriteria()).isEqualTo(exam.getExamDetail().getPassingCriteria())
        );
    }

    @Test
    @DisplayName("관리자는 시험을 추가한다.")
    void add_exam() {
        ExamCreateRequest request = createExamCreateRequest("exam1");
        given(examRepositoryPort.save(any(Exam.class))).willAnswer(this::simulateSavingExamWithId);

        CreateExamResponse result = examService.create(request);

        verify(examRepositoryPort, times(1)).save(any(Exam.class));
        assertAll(
                () -> assertThat(result.examId()).isNotNull(),
                () -> assertThat(result.title()).isEqualTo(request.title()),
                () -> assertThat(result.category()).isEqualTo(request.category()),
                () -> assertThat(result.authority()).isEqualTo(request.authority()),
                () -> assertThat(result.subjects()).isEqualTo(request.subjects()),
                () -> assertThat(result.certificationType()).isEqualTo(request.certificationType()),
                () -> assertThat(result.preparation()).isEqualTo(request.preparation()),
                () -> assertThat(result.eligibility()).isEqualTo(request.eligibility()),
                () -> assertThat(result.examStructure()).isEqualTo(request.examStructure()),
                () -> assertThat(result.passingCriteria()).isEqualTo(request.passingCriteria())
        );
    }

    private Exam simulateSavingExamWithId(InvocationOnMock invocation) {
        return TestUtils.withSetup(
                () -> invocation.getArgument(0),
                exam -> setField(exam, "id", 1L)
        );
    }

    @Test
    @DisplayName("관리자는 시험을 수정한다.")
    void update_exam() {
        Long examId = 1L;
        Exam exam = getExistingExamById(examId);
        ExamUpdateRequest request = createExamUpdateRequest("수정된 시험");
        given(examRepositoryPort.getById(any(Long.class))).willReturn(exam);

        examService.update(examId, request);

        verify(examRepositoryPort, times(1)).getById(any(Long.class));
        assertAll(
                () -> assertThat(exam.getTitle()).isEqualTo(request.title()),
                () -> assertThat(exam.getCategory()).isEqualTo(request.category()),
                () -> assertThat(exam.getCertificationType()).isEqualTo(request.certificationType()),
                () -> assertThat(exam.getAuthority()).isEqualTo(request.authority()),
                () -> assertThat(exam.getSubjects().stream().map(SubjectDto::new)).isEqualTo(request.subjects()),
                () -> assertThat(exam.getExamDetail().getPreparation()).isEqualTo(request.preparation()),
                () -> assertThat(exam.getExamDetail().getEligibility()).isEqualTo(request.eligibility()),
                () -> assertThat(exam.getExamDetail().getExamStructure()).isEqualTo(request.examStructure()),
                () -> assertThat(exam.getExamDetail().getPassingCriteria()).isEqualTo(request.passingCriteria())
        );
    }


    @Test
    @DisplayName("관리자는 시험을 삭제한다.")
    void delete_exam() {
        Long examId = 1L;
        willDoNothing().given(examRepositoryPort).deleteById(1L);

        examService.delete(1L);
        //TODO: 상수 비교 이외의 방법으로 response.message 의 상태 검증을 하면 좋을듯함
        verify(examRepositoryPort, times(1)).deleteById(any(Long.class));
    }

    private Exam getExistingExamById(Long examId) {
        Exam exam = createExam("exam1");
        setField(exam, "id", examId);
        return exam;
    }
}
