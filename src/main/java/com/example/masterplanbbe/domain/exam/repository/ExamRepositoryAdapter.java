package com.example.masterplanbbe.domain.exam.repository;

import com.example.masterplanbbe.common.page.CustomPage;
import com.example.masterplanbbe.common.request.CustomPageRequest;
import com.example.masterplanbbe.domain.exam.dto.ExamItemCardDto;
import com.example.masterplanbbe.domain.exam.dto.ExamWithDetailsDto;
import com.example.masterplanbbe.domain.exam.dto.QExamWithDetailsDto;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.enums.ExamSortOption;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.masterplanbbe.common.exception.GlobalException.*;
import static com.example.masterplanbbe.common.exception.ErrorCode.*;
import static com.example.masterplanbbe.domain.exam.entity.QExam.exam;
import static com.example.masterplanbbe.domain.exam.entity.QExamDetail.*;
import static com.example.masterplanbbe.domain.specBookmark.entity.QSpecBookmark.*;

@Repository
@RequiredArgsConstructor
public class ExamRepositoryAdapter implements ExamRepositoryPort, ExamRepositoryCustom {
    private final ExamRepository examRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public CustomPage<ExamItemCardDto> getExamItemCards(CustomPageRequest<ExamSortOption> request,
                                                        String email) {
        return null;
    }

    @Override
    public ExamWithDetailsDto getExamWithDetails(Long examId,
                                                 String email) {
        return Optional.ofNullable(
                queryFactory.select(new QExamWithDetailsDto(
                                exam.name,
                                exam.examDetail.spec.issuingOrganization,
                                exam.examDetail.spec.certificationType,
                                specBookmark.isNotNull(),
                                exam.examDetail.preparation,
                                exam.examDetail.eligibility,
                                exam.examDetail.examStructure,
                                exam.examDetail.passingCriteria
                        ))
                        .from(exam)
                        .where(exam.id.eq(examId))
                        .leftJoin(examDetail)
                        .on(examDetail.id.eq(exam.examDetail.id)).fetchJoin()
                        .leftJoin(specBookmark)
                        .on(specBookmark.spec.id.eq(exam.examDetail.spec.id).and(specBookmark.member.email.eq(email)))
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
