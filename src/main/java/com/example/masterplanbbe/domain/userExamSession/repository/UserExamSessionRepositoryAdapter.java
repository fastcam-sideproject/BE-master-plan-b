package com.example.masterplanbbe.domain.userExamSession.repository;

import com.example.masterplanbbe.common.GlobalException.NotFoundException;
import com.example.masterplanbbe.domain.userExamSession.entity.UserExamSession;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.masterplanbbe.common.exception.ErrorCode.NOT_FOUND_USER_EXAM_SESSION;

@Repository
@RequiredArgsConstructor
public class UserExamSessionRepositoryAdapter implements UserExamSessionRepositoryPort {
    private final UserExamSessionRepository userExamSessionRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserExamSession save(UserExamSession userExamSession) {
        return userExamSessionRepository.save(userExamSession);
    }

    @Override
    public UserExamSession getById(Long id) {
        return userExamSessionRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_USER_EXAM_SESSION));
    }
}
