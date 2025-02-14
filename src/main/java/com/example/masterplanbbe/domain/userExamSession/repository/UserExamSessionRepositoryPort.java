package com.example.masterplanbbe.domain.userExamSession.repository;

import com.example.masterplanbbe.domain.userExamSession.entity.UserExamSession;

public interface UserExamSessionRepositoryPort {
    UserExamSession save(UserExamSession userExamSession);

    UserExamSession getById(Long id);

    void delete(UserExamSession userExamSession);

    UserExamSession findByIdAndMemberId(Long id, Long memberId);
}
