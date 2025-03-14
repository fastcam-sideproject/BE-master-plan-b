package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.application.dto.QExamItemCardDto;
import com.example.masterplanbbe.application.dto.QExamWithDetailsDto;
import com.example.masterplanbbe.domain.repository.ExamRepository;
import com.example.masterplanbbe.domain.repository.ExamRepositoryCustom;
import com.example.masterplanbbe.domain.repository.ExamRepositoryPort;
import com.example.masterplanbbe.infrastructure.sort.page.CustomPage;
import com.example.masterplanbbe.presentation.request.CustomPageRequest;
import com.example.masterplanbbe.infrastructure.sort.util.CustomPageUtils;
import com.example.masterplanbbe.infrastructure.sort.util.SortUtil;
import com.example.masterplanbbe.application.dto.ExamItemCardDto;
import com.example.masterplanbbe.application.dto.ExamWithDetailsDto;
import com.example.masterplanbbe.domain.entity.Exam;
import com.example.masterplanbbe.domain.enums.ExamSortOption;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;

import static com.example.masterplanbbe.infrastructure.exception.GlobalException.*;
import static com.example.masterplanbbe.infrastructure.exception.ErrorCode.*;
import static com.example.masterplanbbe.domain.entity.QExam.exam;
import static com.example.masterplanbbe.domain.entity.QExamDetail.*;
import static com.example.masterplanbbe.domain.entity.QSpecBookmark.*;

@Repository
@RequiredArgsConstructor
public class ExamRepositoryAdapter implements ExamRepositoryPort, ExamRepositoryCustom {
    private final ExamRepository examRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public CustomPage<ExamItemCardDto> getExamItemCards(CustomPageRequest<ExamSortOption> request,
                                                        String email) {
        List<ExamItemCardDto> list = queryFactory
                .select(new QExamItemCardDto(
                        exam.name,
                        exam.examDetail.spec.specCategory,
                        exam.applyStartDate,
                        exam.examStartDate,
                        specBookmark.isNotNull()
                ))
                .from(exam)
                .leftJoin(examDetail)
                .on(examDetail.id.eq(exam.examDetail.id)).fetchJoin()
                .leftJoin(specBookmark)
                .on(specBookmark.spec.id.eq(exam.examDetail.spec.id).and(specBookmark.member.email.eq(email)))
                .orderBy(SortUtil.getOrderSpecifier(request.sort(), request.isAsc()))
                .offset(request.getOffset())
                .limit(request.size())
                .fetch();

        LongSupplier countQuery = () -> Optional.ofNullable(queryFactory
                .select(exam.count())
                .from(exam)
                .fetchOne()).orElse(0L);

        return CustomPageUtils.getPage(list, request, countQuery);
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
