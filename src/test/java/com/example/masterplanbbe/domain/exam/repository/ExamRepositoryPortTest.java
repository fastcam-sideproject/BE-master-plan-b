package com.example.masterplanbbe.domain.exam.repository;

import com.example.masterplanbbe.common.exception.GlobalException;
import com.example.masterplanbbe.domain.exam.dto.ExamItemCardDto;
import com.example.masterplanbbe.domain.exam.dto.ExamWithDetailsDto;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.examBookmark.entity.ExamBookmark;
import com.example.masterplanbbe.domain.examBookmark.repository.ExamBookmarkRepository;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.example.masterplanbbe.common.exception.ErrorCode.*;
import static com.example.masterplanbbe.domain.fixture.ExamFixture.createExam;
import static com.example.masterplanbbe.domain.fixture.MemberFixture.createMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@DisplayName("시험 레포지토리 테스트")
public class ExamRepositoryPortTest {
    @Autowired private ExamRepositoryPort examRepositoryPort;
    @Autowired private MemberRepository memberRepository;
    @Autowired private ExamBookmarkRepository examBookmarkRepository;

    @BeforeEach
    void setUp() {
        examBookmarkRepository.deleteAll();
        examRepositoryPort.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자는 시험을 조회하고 북마크 여부를 확인할 수 있다.")
    void retrieve_exam_and_check_bookmark_status() {
        Member member = memberRepository.save(createMember());
        Exam exam1 = createExam("exam1");
        Exam exam2 = createExam("exam2");
        examRepositoryPort.saveAll(List.of(exam1, exam2));
        ExamBookmark examBookmark = examBookmarkRepository.save(new ExamBookmark(member, exam1));
        PageRequest pageRequest = PageRequest.of(0, 25);

        Page<ExamItemCardDto> result = examRepositoryPort.getExamItemCards(pageRequest, member.getUserId());

        assertThat(result.getContent().size()).isEqualTo(2);
        assertAll(
                () -> assertThat(result.getContent().get(0).title()).isEqualTo("exam2"),
                () -> assertThat(result.getContent().get(1).title()).isEqualTo("exam1"),
                () -> assertThat(result.getContent().get(0).isBookmarked()).isFalse(),
                () -> assertThat(result.getContent().get(1).isBookmarked()).isTrue()
        );
    }

    @Test
    @DisplayName("사용자는 시험의 상세 정보를 조회할 수 있다.")
    void retrieve_exam_detail() {
        Exam exam = createExam("exam");
        examRepositoryPort.save(exam);

        ExamWithDetailsDto result = examRepositoryPort.getExamWithDetails(exam.getId());

        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.title()).isEqualTo(exam.getTitle()),
                () -> assertThat(result.category()).isEqualTo(exam.getCategory()),
                () -> assertThat(result.certificationType()).isEqualTo(exam.getCertificationType()),
                () -> assertThat(result.authority()).isEqualTo(exam.getAuthority()),
                () -> assertThat(result.difficulty()).isEqualTo(exam.getDifficulty()),
                () -> assertThat(result.participantCount()).isEqualTo(exam.getParticipantCount()),
                () -> assertThat(result.preparation()).isEqualTo(exam.getExamDetail().getPreparation()),
                () -> assertThat(result.eligibility()).isEqualTo(exam.getExamDetail().getEligibility()),
                () -> assertThat(result.examStructure()).isEqualTo(exam.getExamDetail().getExamStructure()),
                () -> assertThat(result.passingCriteria()).isEqualTo(exam.getExamDetail().getPassingCriteria())
        );
    }

    @Test
    @DisplayName("존재하지 않는 시험의 상세 정보를 조회하면 예외를 발생시킨다.")
    void throw_exception_when_exam_detail_not_found() {
        Exam exam = createExam("exam");
        examRepositoryPort.save(exam);

        assertThatThrownBy(() -> examRepositoryPort.getExamWithDetails(-1L))
                .isInstanceOf(GlobalException.NotFoundException.class)
                .hasMessageContaining(EXAM_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("요청한 id의 시험이 없으면 예외를 발생시킨다.")
    void throw_exception_when_exam_not_found() {
        Exam exam = createExam("exam");

        examRepositoryPort.save(exam);

        assertThatThrownBy(() -> examRepositoryPort.getById(-1L))
                .isInstanceOf(GlobalException.NotFoundException.class)
                .hasMessageContaining(EXAM_NOT_FOUND.getMessage());
    }

}
