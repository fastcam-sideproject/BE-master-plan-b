package com.example.masterplanbbe.domain.userExamSession.repository;

import com.example.masterplanbbe.common.GlobalException.NotFoundException;
import com.example.masterplanbbe.domain.userExamSession.dto.response.UserExamSessionDetailResponse;
import com.example.masterplanbbe.domain.userExamSession.entity.UserExamSession;
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
    public UserExamSession findByIdAndMemberUserId(Long id, String memberId) {
        return userExamSessionRepository.findByIdAndMemberUserId(id, memberId).orElseThrow(
                () -> new NotFoundException(NOT_FOUND_USER_EXAM_SESSION)
        );

    }

    @Override
    public UserExamSessionDetailResponse findDetailByIdAndMemberId(Long id, String memberId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .select(Projections.constructor(UserExamSessionDetailResponse.class,
                                userExamSession.id,
                                userExamSession.member.userId,
                                userExamSession.exam.certificationType,
                                userExamSession.exam.title,
                                userExamSession.date,
                                Expressions.numberTemplate(Long.class, "DATEDIFF({0}, {1})", LocalDate.now(), userExamSession.date)
                        ))
                        .from(userExamSession)
                        .where(userExamSession.id.eq(id)
                                .and(userExamSession.member.userId.eq(memberId)))
                        .fetchOne()
        ).orElseThrow(() -> new NotFoundException(NOT_FOUND_USER_EXAM_SESSION));
    }

    @Override
    public Page<UserExamSessionDetailResponse> findDetailsByYearAndMonthAndMemberId(Integer year, Integer month, String memberId, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (year != null) {
            builder.and(userExamSession.date.year().eq(year));
        }

        if (month != null) {
            builder.and(userExamSession.date.month().eq(month));
        }

        builder.and(userExamSession.member.userId.eq(memberId));

        List<UserExamSessionDetailResponse> results = jpaQueryFactory
                .select(Projections.constructor(UserExamSessionDetailResponse.class,
                        userExamSession.id,
                        userExamSession.member.userId,
                        userExamSession.exam.certificationType,
                        userExamSession.exam.title,
                        userExamSession.date,
                        Expressions.numberTemplate(Long.class, "DATEDIFF({0}, {1})", LocalDate.now(), userExamSession.date)
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
