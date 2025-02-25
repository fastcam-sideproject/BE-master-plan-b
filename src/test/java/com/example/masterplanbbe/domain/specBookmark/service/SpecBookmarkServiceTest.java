package com.example.masterplanbbe.domain.specBookmark.service;

import com.example.masterplanbbe.domain.spec.repository.SpecRepositoryPort;
import com.example.masterplanbbe.domain.specBookmark.repository.SpecBookmarkRepository;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
@DisplayName("시험 북마크 서비스 테스트")
public class SpecBookmarkServiceTest {
    @InjectMocks
    SpecBookmarkService specBookmarkService;
    @Mock
    SpecBookmarkRepository specBookmarkRepository;
    @Mock
    MemberRepositoryPort memberRepositoryPort;
    @Mock
    SpecRepositoryPort specRepositoryPort;

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


    @Test
    @DisplayName("사용자는 시험 북마크를 삭제한다.")
    void delete_exam_bookmark() {
        Long examBookmarkId = 1L;
        willDoNothing().given(examBookmarkRepository).deleteById(examBookmarkId);

        examBookmarkService.deleteExamBookmark(examBookmarkId);

        verify(examBookmarkRepository, times(1)).deleteById(anyLong());
    }

    private Spec getExistingSpec() {
        return TestUtils.getExistingEntity(() -> createSpec("Spec1"));
    }
    private Exam getExistingExam() {
        return withSetup(() -> createExam("exam1"), instance -> setField(instance, "id", 1L));
    }
}
