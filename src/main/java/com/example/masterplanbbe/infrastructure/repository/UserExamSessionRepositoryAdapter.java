package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.domain.repository.UserExamSessionRepository;
import com.example.masterplanbbe.domain.repository.UserExamSessionRepositoryPort;
import com.example.masterplanbbe.infrastructure.exception.GlobalException.NotFoundException;
import com.example.masterplanbbe.presentation.response.UserExamSessionDetailResponse;
import com.example.masterplanbbe.domain.entity.UserExamSession;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;

import static com.example.masterplanbbe.infrastructure.exception.ErrorCode.NOT_FOUND_USER_EXAM_SESSION;
import static com.example.masterplanbbe.domain.entity.QUserExamSession.userExamSession;

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
    public UserExamSession findByIdAndMemberUserId(Long id, String memberId) {
        return userExamSessionRepository.findByIdAndMemberEmail(id, memberId).orElseThrow(
                () -> new NotFoundException(NOT_FOUND_USER_EXAM_SESSION)
        );

    }

    @Override
    public UserExamSessionDetailResponse findDetailByIdAndMemberId(Long id, String memberId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .select(Projections.constructor(UserExamSessionDetailResponse.class,
                                userExamSession.id,
                                userExamSession.member.email,
                                userExamSession.exam.examDetail.spec.certificationType,
                                userExamSession.exam.name,
                                userExamSession.exam.examStartDate,
                                Expressions.numberTemplate(Long.class, "DATEDIFF({0}, {1})", LocalDate.now(), userExamSession.exam.examStartDate)
                        ))
                        .from(userExamSession)
                        .where(userExamSession.id.eq(id)
                                .and(userExamSession.member.email.eq(memberId)))
                        .fetchOne()
        ).orElseThrow(() -> new NotFoundException(NOT_FOUND_USER_EXAM_SESSION));
    }

    @Override
    public Page<UserExamSessionDetailResponse> findDetailsByYearAndMonthAndMemberId(Integer year, Integer month, String memberId, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (year != null) {
            builder.and(userExamSession.exam.examStartDate.year().eq(year));
        }

        if (month != null) {
            builder.and(userExamSession.exam.examStartDate.month().eq(month));
        }

        builder.and(userExamSession.member.email.eq(memberId));

        List<UserExamSessionDetailResponse> results = jpaQueryFactory
                .select(Projections.constructor(UserExamSessionDetailResponse.class,
                        userExamSession.id,
                        userExamSession.member.email,
                        userExamSession.exam.examDetail.spec.certificationType,
                        userExamSession.exam.name,
                        userExamSession.exam.examStartDate,
                        Expressions.numberTemplate(Long.class, "DATEDIFF({0}, {1})", LocalDate.now(), userExamSession.exam.examStartDate)
                ))
                .from(userExamSession)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        LongSupplier count = () -> Optional.ofNullable(jpaQueryFactory
                .select(userExamSession.count())
                .from(userExamSession)
                .where(builder)
                .fetchOne()
        ).orElse(0L);

        return PageableExecutionUtils.getPage(results, pageable, count);
    }
}
