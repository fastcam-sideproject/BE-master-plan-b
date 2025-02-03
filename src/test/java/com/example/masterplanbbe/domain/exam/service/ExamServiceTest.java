package com.example.masterplanbbe.domain.exam.service;

import com.example.masterplanbbe.domain.exam.dto.ExamItemCardDto;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.entity.ExamBookmark;
import com.example.masterplanbbe.domain.exam.repository.ExamRepositoryPort;
import com.example.masterplanbbe.domain.exam.request.ExamCreateRequest;
import com.example.masterplanbbe.domain.exam.response.CreateExamResponse;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static com.example.masterplanbbe.domain.fixture.ExamFixture.createExam;
import static com.example.masterplanbbe.domain.fixture.ExamFixture.createExamCreateRequest;
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
    @DisplayName("관리자는 시험을 추가한다.")
    void add_exam() {
        ExamCreateRequest request = createExamCreateRequest("exam1");
        given(examRepositoryPort.save(any(Exam.class))).willAnswer(this::simulateSavingExamWithId);

        CreateExamResponse result = examService.create(request);

        verify(examRepositoryPort, times(1)).save(any(Exam.class));
        assertAll(
                () -> assertThat(result.examId()).isNotNull(),
                () -> assertThat(result.title()).isEqualTo("exam1"),
                () -> assertThat(result.category()).isEqualTo(request.category()),
                () -> assertThat(result.authority()).isEqualTo(request.authority()),
                () -> assertThat(result.subjects()).isEqualTo(request.subjects())
        );
    }

    private Exam simulateSavingExamWithId(InvocationOnMock invocation) {
        return TestUtils.withSetup(
                () -> invocation.getArgument(0),
                exam -> setField(exam, "id", 1L)
        );
    }
}
