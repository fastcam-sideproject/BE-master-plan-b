package com.example.masterplanbbe.domain.exam.repository;

import com.example.masterplanbbe.domain.exam.dto.ExamItemCardDto;
import com.example.masterplanbbe.domain.exam.dto.QExamItemCardDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;

import static com.example.masterplanbbe.domain.exam.entity.QExam.exam;
import static com.example.masterplanbbe.domain.exam.entity.QExamBookmark.examBookmark;


public class ExamRepositoryImpl implements ExamRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ExamRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ExamItemCardDto> getExamItemCards(Pageable pageable,
                                                  String memberId) {
        List<ExamItemCardDto> queryResult = queryFactory.select(
                        new QExamItemCardDto(
                                exam,
                                examBookmark.isNotNull()
                        )
                )
                .from(exam)
                .leftJoin(examBookmark).fetchJoin()
                .on(exam.id.eq(examBookmark.exam.id))
                .orderBy(exam.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        LongSupplier countQuery = () -> Optional.ofNullable(
                queryFactory.select(
                        exam.count()
                )
                .from(exam)
                .offset(pageable.getOffset())
                .fetchOne()
        ).orElse(0L);

        return PageableExecutionUtils.getPage(queryResult, pageable, countQuery);
    }
}
