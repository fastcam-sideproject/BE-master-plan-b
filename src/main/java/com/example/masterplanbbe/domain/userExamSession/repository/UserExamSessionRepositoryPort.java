package com.example.masterplanbbe.domain.userExamSession.repository;

import com.example.masterplanbbe.domain.userExamSession.entity.UserExamSession;

public interface UserExamSessionRepositoryPort {
    UserExamSession save(UserExamSession userExamSession);
}
