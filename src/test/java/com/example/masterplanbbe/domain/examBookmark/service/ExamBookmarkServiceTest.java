package com.example.masterplanbbe.domain.examBookmark.service;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.repository.ExamRepositoryPort;
import com.example.masterplanbbe.domain.examBookmark.entity.ExamBookmark;
import com.example.masterplanbbe.domain.examBookmark.repository.ExamBookmarkRepository;
import com.example.masterplanbbe.domain.examBookmark.response.CreateExamBookmarkResponse;
import com.example.masterplanbbe.domain.fixture.ExamFixture;
import com.example.masterplanbbe.domain.fixture.MemberFixture;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepositoryPort;
import com.example.masterplanbbe.utils.TestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.masterplanbbe.domain.fixture.ExamFixture.createExam;
import static com.example.masterplanbbe.domain.fixture.MemberFixture.createMember;
import static com.example.masterplanbbe.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
@DisplayName("시험 북마크 서비스 테스트")
public class ExamBookmarkServiceTest {
    @InjectMocks
    ExamBookmarkService examBookmarkService;
    @Mock
    ExamBookmarkRepository examBookmarkRepository;
    @Mock
    MemberRepositoryPort memberRepositoryPort;
    @Mock
    ExamRepositoryPort examRepositoryPort;

    @Test
    @DisplayName("사용자는 시험 북마크를 추가한다.")
    void add_exam_bookmark() {
        Member member = getExistingMember();
        Exam exam = getExistingExam();
        given(memberRepositoryPort.findById(anyLong())).willReturn(member);
        given(examRepositoryPort.getById(anyLong())).willReturn(exam);
        given(examBookmarkRepository.save(any(ExamBookmark.class))).willAnswer(this::simulateSavingExamBookmark);

        CreateExamBookmarkResponse result = examBookmarkService.createExamBookmark(member.getId(), exam.getId());

        verify(memberRepositoryPort, times(1)).findById(anyLong());
        verify(examRepositoryPort, times(1)).getById(anyLong());
        verify(examBookmarkRepository, times(1)).save(any(ExamBookmark.class));
        assertAll(
                () -> assertThat(result.examBookmarkId()).isNotNull(),
                () -> assertThat(result.memberId()).isEqualTo(member.getId()),
                () -> assertThat(result.examId()).isEqualTo(exam.getId())
        );
    }

    private ExamBookmark simulateSavingExamBookmark(InvocationOnMock invocation) {
        return withSetup(
                () -> invocation.getArgument(0),
                    examBookmark -> setField(examBookmark, "id", 1L)
        );
    }

    private Member getExistingMember() {
        return withSetup(MemberFixture::createMember, instance -> setField(instance, "id", 1L));
    }

    private Exam getExistingExam() {
        return withSetup(() -> createExam("exam1"), instance -> setField(instance, "id", 1L));
    }

    @Test
    @DisplayName("사용자는 시험 북마크를 삭제한다.")
    void delete_exam_bookmark() {
        Long examBookmarkId = 1L;
        willDoNothing().given(examBookmarkRepository).deleteById(examBookmarkId);

        examBookmarkService.deleteExamBookmark(examBookmarkId);

        verify(examBookmarkRepository, times(1)).deleteById(anyLong());
    }

}
