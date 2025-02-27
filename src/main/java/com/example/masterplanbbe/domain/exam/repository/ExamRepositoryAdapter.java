package com.example.masterplanbbe.domain.exam.repository;

import com.example.masterplanbbe.domain.exam.dto.ExamItemCardDto;
import com.example.masterplanbbe.domain.exam.dto.ExamWithDetailsDto;
import com.example.masterplanbbe.domain.exam.dto.QExamWithDetailsDto;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.spec.entity.QSpec;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;

import static com.example.masterplanbbe.common.exception.GlobalException.*;
import static com.example.masterplanbbe.common.exception.ErrorCode.*;
import static com.example.masterplanbbe.domain.exam.entity.QExam.exam;

@Repository
@RequiredArgsConstructor
public class ExamRepositoryAdapter implements ExamRepositoryPort, ExamRepositoryCustom {
    private final ExamRepository examRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ExamItemCardDto> getExamItemCards(Pageable pageable,
                                                  Long memberId) {
        return null;
    }

    @Override
    public ExamWithDetailsDto getExamWithDetails(Long examId) {
        return Optional.ofNullable(
                queryFactory.select(new QExamWithDetailsDto(exam,
                                QSpec.spec))
                        .from(exam)
                        .leftJoin(exam.examDetail).fetchJoin()
                        .where(exam.id.eq(examId))
                        .fetchOne()
        ).orElseThrow(() -> new NotFoundException(EXAM_NOT_FOUND));
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
    public void deleteById(Long examId) {
        examRepository.deleteById(examId);
    }

    @Override
    public void deleteAll() {
        examRepository.deleteAll();
    }
}
