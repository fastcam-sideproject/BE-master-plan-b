package com.example.masterplanbbe.domain.userExamSession.service;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.repository.ExamRepositoryPort;
import com.example.masterplanbbe.domain.userExamSession.dto.request.UserExamSessionCreateRequest;
import com.example.masterplanbbe.domain.userExamSession.dto.response.UserExamSessionResponse;
import com.example.masterplanbbe.domain.userExamSession.entity.UserExamSession;
import com.example.masterplanbbe.domain.userExamSession.repository.UserExamSessionRepositoryPort;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserExamSessionService {

    private final ExamRepositoryPort examRepositoryPort;
    private final MemberRepositoryPort memberRepositoryPort;
    private final UserExamSessionRepositoryPort userExamSessionRepositoryPort;

    @Transactional
    public UserExamSessionResponse create(UserExamSessionCreateRequest request, Long memberId) {
        Exam exam = examRepositoryPort.getById(request.examId());

        Member member = memberRepositoryPort.findById(memberId);

        UserExamSession userExamSession = userExamSessionRepositoryPort.save(UserExamSession.of(exam, member, request.date(), request.startTime(), request.endTime()));

        return UserExamSessionResponse.from(userExamSession);
    }
}
