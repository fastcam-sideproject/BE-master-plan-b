package com.example.masterplanbbe.domain.spec.repository;

import com.example.masterplanbbe.domain.spec.dto.QSpecItemCardDto;
import com.example.masterplanbbe.domain.spec.dto.QSpecWithDetailsDto;
import com.example.masterplanbbe.domain.spec.dto.SpecItemCardDto;
import com.example.masterplanbbe.domain.spec.dto.SpecWithDetailsDto;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLSubQuery;
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

import static com.example.masterplanbbe.common.exception.ErrorCode.SPEC_NOT_FOUND;
import static com.example.masterplanbbe.common.exception.GlobalException.*;
import static com.example.masterplanbbe.domain.exam.entity.QExam.*;
import static com.example.masterplanbbe.domain.exam.entity.QExamDetail.examDetail;
import static com.example.masterplanbbe.domain.spec.entity.QSpec.*;
import static com.example.masterplanbbe.domain.specBookmark.entity.QSpecBookmark.specBookmark;

@Repository
@RequiredArgsConstructor
public class SpecRepositoryAdapter implements SpecRepositoryPort, SpecRepositoryCustom {
    private final SpecRepository specRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SpecItemCardDto> getSpecItemCards(Pageable pageable,
                                                  Long memberId) {
        LocalDate today = LocalDate.now();

        JPQLSubQuery<Long> closestExamIdSubquery = JPAExpressions
                .select(exam.id)
                .from(exam)
                .join(exam.examDetail, examDetail)
                .where(
                        examDetail.spec.id.eq(spec.id)
                                .and(exam.applyEndDate.goe(today))
                )
                .orderBy(exam.examStartDate.asc())
                .limit(1);

        List<SpecItemCardDto> list = queryFactory
                .select(new QSpecItemCardDto(
                        spec,
                        exam,
                        specBookmark.isNotNull()
                ))
                .from(spec)
                .leftJoin(specBookmark)
                .on(specBookmark.spec.id.eq(spec.id).and(specBookmark.member.id.eq(memberId)))
                .leftJoin(exam)
                .on(exam.id.eq(closestExamIdSubquery))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        LongSupplier countQuery = () -> Optional.ofNullable(
                        queryFactory
                                .select(spec.count())
                                .from(spec)
                                .fetchOne())
                .orElse(0L);

        return PageableExecutionUtils.getPage(list, pageable, countQuery);
    }

    @Override
    public SpecWithDetailsDto getSpecWithDetails(Long specId) {
        return queryFactory
                .select(
                        new QSpecWithDetailsDto(
                                spec,
                                examDetail,
                                specBookmark.isNotNull()
                        )
                )
                .from(spec)
                .leftJoin(specBookmark)
                .on(specBookmark.spec.id.eq(spec.id))
                .leftJoin(examDetail)
                .on(examDetail.spec.id.eq(spec.id))
                .fetchOne();
    }

    @Override
    public Spec getById(Long specId) {
        return specRepository.findById(specId).orElseThrow(() -> new NotFoundException(SPEC_NOT_FOUND));
    }

    @Override
    public Spec save(Spec spec) {
        return specRepository.save(spec);
    }

    @Override
    public void saveAll(Iterable<Spec> specs) {
        specRepository.saveAll(specs);
    }

    @Override
    public void deleteById(Long specId) {
        specRepository.deleteById(specId);
    }

    @Override
    public void deleteAll() {
        specRepository.deleteAll();
    }

}
