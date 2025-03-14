package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.domain.entity.Exam;
import com.example.masterplanbbe.domain.repository.ExamRepositoryPort;
import com.example.masterplanbbe.presentation.request.UserExamSessionRequest;
import com.example.masterplanbbe.presentation.response.UserExamSessionDetailResponse;
import com.example.masterplanbbe.presentation.response.UserExamSessionResponse;
import com.example.masterplanbbe.domain.entity.UserExamSession;
import com.example.masterplanbbe.domain.repository.UserExamSessionRepositoryPort;
import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.repository.MemberRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserExamSessionService {

    private final ExamRepositoryPort examRepositoryPort;
    private final MemberRepositoryPort memberRepositoryPort;
    private final UserExamSessionRepositoryPort userExamSessionRepositoryPort;

    @Transactional
    public UserExamSessionResponse create(UserExamSessionRequest request, String memberId) {
        Exam exam = examRepositoryPort.getById(request.examId());

        Member member = memberRepositoryPort.findByEmail(memberId);

        UserExamSession userExamSession = userExamSessionRepositoryPort.save(UserExamSession.of(exam, member, request.startTime(), request.endTime()));

        return UserExamSessionResponse.from(userExamSession);
    }

    @Transactional
    public UserExamSessionResponse update(UserExamSessionRequest request, Long updateId) {
        UserExamSession userExamSession = userExamSessionRepositoryPort.getById(updateId);

        Exam exam = examRepositoryPort.getById(request.examId());

        userExamSession.updateUserExamSession(request, exam);

        return UserExamSessionResponse.from(userExamSession);
    }

    @Transactional
    public void delete(Long deleteId, String memberId) {
        UserExamSession userExamSession = userExamSessionRepositoryPort.findByIdAndMemberUserId(deleteId, memberId);
        userExamSessionRepositoryPort.delete(userExamSession);
    }

    public UserExamSessionDetailResponse findOne(Long id, String memberId) {
        return userExamSessionRepositoryPort.findDetailByIdAndMemberId(id, memberId);
    }

    public Page<UserExamSessionDetailResponse> findAll(Integer year, Integer month, String memberId, Pageable pageable) {
        return userExamSessionRepositoryPort.findDetailsByYearAndMonthAndMemberId(year, month, memberId, pageable);
    }
}
