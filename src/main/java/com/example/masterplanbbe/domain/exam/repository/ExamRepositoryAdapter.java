package com.example.masterplanbbe.domain.exam.repository;

import com.example.masterplanbbe.domain.exam.dto.ExamItemCardDto;
import com.example.masterplanbbe.domain.exam.dto.QExamItemCardDto;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;

import static com.example.masterplanbbe.common.GlobalException.*;
import static com.example.masterplanbbe.common.exception.ErrorCode.*;
import static com.example.masterplanbbe.domain.exam.entity.QExam.exam;
import static com.example.masterplanbbe.domain.exam.entity.QExamBookmark.examBookmark;


@Repository
@RequiredArgsConstructor
public class ExamRepositoryAdapter implements ExamRepositoryPort, ExamRepositoryCustom {
    private final ExamRepository examRepository;
    private final JPAQueryFactory queryFactory;

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

    @Override
    public Exam getById(Long examId) {
        return examRepository.findById(examId).orElseThrow(() -> new NotFoundException(EXAM_NOT_FOUND));
    }

    @Override
    public Exam save(Exam exam) {
        return examRepository.save(exam);
    }

    @Override
    public void saveAll(Iterable<Exam> exams) {
        examRepository.saveAll(exams);
    }

    @Override
    public void deleteAll() {
        examRepository.deleteAll();
    }
}
