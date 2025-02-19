package com.example.masterplanbbe.domain.examCertifications.service;


import com.example.masterplanbbe.common.exception.GlobalException;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.repository.ExamRepository;
import com.example.masterplanbbe.domain.examCertifications.dto.request.ExamCertificationCreateRequest;
import com.example.masterplanbbe.domain.examCertifications.dto.response.ExamCertificationCreateResponse;
import com.example.masterplanbbe.domain.examCertifications.enums.PriorLearningLevel;
import com.example.masterplanbbe.domain.examCertifications.repository.ExamCertificationRepository;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.masterplanbbe.domain.fixture.ExamFixture.*;
import static com.example.masterplanbbe.domain.fixture.MemberFixture.*;
import static com.example.masterplanbbe.domain.fixture.ExamCertificationFixture.*;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("자격 인증 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class ExamCertificationServiceTest {
    @InjectMocks
    ExamCertificationService examCertificationService;

    @Mock
    ExamCertificationRepository examCertificationRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    ExamRepository examRepository;

    @Nested
    @DisplayName("자격 인증 생성 테스트")
    public class CreateExamCertificationTest {
        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            ExamCertificationCreateRequest createRequest = new ExamCertificationCreateRequest(
                    PriorLearningLevel.NON_MAJOR,
                    10,
                    "6달"
            );

            Exam exam = createExam(10L);
            Member member = createMember("userID");

            BDDMockito.given(memberRepository.findByUserId(member.getUserId()))
                    .willReturn(Optional.of(member));
            BDDMockito.given(examRepository.getReferenceById(exam.getId()))
                    .willReturn(exam);
            BDDMockito.given(examCertificationRepository.save(any()))
                    .willReturn(createExamCertificationWithMemberAndExam(createRequest, member, exam));

            //when
            ExamCertificationCreateResponse result = examCertificationService.create(
                    createRequest, exam.getId(), member.getUserId());

            //then
            Assertions.assertThat(result.exam().title()).isEqualTo(exam.getTitle());
            Assertions.assertThat(result.exam().examId()).isEqualTo(exam.getId());
            Assertions.assertThat(result.member().nickname()).isEqualTo(member.getNickname());
            Assertions.assertThat(result.member().profileImageUrl()).isEqualTo(member.getProfileImageUrl());
            Assertions.assertThat(result.learningPeriod()).isEqualTo(createRequest.learningPeriod());
            Assertions.assertThat(result.dailyStudyHours()).isEqualTo(createRequest.dailyStudyHours());
            Assertions.assertThat(result.priorLearningLevel()).isEqualTo(createRequest.priorLearningLevel());
        }

        @DisplayName("해당 회원이 없을때 ")
        @Test
        public void notExistMember() throws Exception {
            //given
            ExamCertificationCreateRequest createRequest = new ExamCertificationCreateRequest(
                    PriorLearningLevel.NON_MAJOR,
                    10,
                    "6달"
            );

            Long examId = 10L;
            String notExistMemberId = "not exist";

            BDDMockito.given(memberRepository.findByUserId(notExistMemberId))
                    .willReturn(Optional.empty());

            //when
            //then
            Assertions.assertThatThrownBy(
                            () -> examCertificationService.create(createRequest, examId, notExistMemberId))
                    .isInstanceOf(GlobalException.NotFoundException.class);
        }
    }

}