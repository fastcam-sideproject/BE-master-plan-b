package com.example.masterplanbbe.domain.userExamSession.repository;

import com.example.masterplanbbe.common.GlobalException.NotFoundException;
import com.example.masterplanbbe.domain.userExamSession.dto.response.UserExamSessionDetailResponse;
import com.example.masterplanbbe.domain.userExamSession.entity.UserExamSession;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

import static com.example.masterplanbbe.common.exception.ErrorCode.NOT_FOUND_USER_EXAM_SESSION;
import static com.example.masterplanbbe.domain.userExamSession.entity.QUserExamSession.userExamSession;

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

    @Override
    public void delete(UserExamSession userExamSession) {
        userExamSessionRepository.delete(userExamSession);
    }

    @Override
    public UserExamSession findByIdAndMemberId(Long id, Long memberId) {
        return userExamSessionRepository.findByIdAndMemberId(id, memberId).orElseThrow(
                () -> new NotFoundException(NOT_FOUND_USER_EXAM_SESSION)
        );

    }

    @Override
    public UserExamSessionDetailResponse findDetailByIdAndMemberId(Long id, Long memberId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .select(Projections.constructor(UserExamSessionDetailResponse.class,
                                userExamSession.id,
                                userExamSession.member.id,
                                userExamSession.exam.certificationType,
                                userExamSession.exam.title,
                                userExamSession.date,
                                Expressions.numberTemplate(Long.class, "DATEDIFF({0}, {1})", LocalDate.now(), userExamSession.date)
                        ))
                        .from(userExamSession)
                        .where(userExamSession.id.eq(id)
                                .and(userExamSession.member.id.eq(memberId)))
                        .fetchOne()
        ).orElseThrow(() -> new NotFoundException(NOT_FOUND_USER_EXAM_SESSION));
    }
}
