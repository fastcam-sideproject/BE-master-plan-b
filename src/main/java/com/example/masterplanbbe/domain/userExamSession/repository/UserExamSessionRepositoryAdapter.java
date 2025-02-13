package com.example.masterplanbbe.domain.userExamSession.repository;

import com.example.masterplanbbe.domain.userExamSession.entity.UserExamSession;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserExamSessionRepositoryAdapter implements UserExamSessionRepositoryPort {
    private final UserExamSessionRepository userExamSessionRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserExamSession save(UserExamSession userExamSession) {
        return userExamSessionRepository.save(userExamSession);
    }
}
