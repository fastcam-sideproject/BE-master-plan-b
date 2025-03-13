package com.example.masterplanbbe.domain.spec.repository;

import com.example.masterplanbbe.common.page.CustomPage;
import com.example.masterplanbbe.common.request.CustomPageRequest;
import com.example.masterplanbbe.common.util.CustomPageUtils;
import com.example.masterplanbbe.common.util.SortUtil;
import com.example.masterplanbbe.domain.spec.dto.QSpecItemCardDto;
import com.example.masterplanbbe.domain.spec.dto.QSpecWithDetailsDto;
import com.example.masterplanbbe.domain.spec.dto.SpecItemCardDto;
import com.example.masterplanbbe.domain.spec.dto.SpecWithDetailsDto;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.spec.enums.SpecSortOption;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;

import static com.example.masterplanbbe.common.exception.ErrorCode.SPEC_NOT_FOUND;
import static com.example.masterplanbbe.common.exception.GlobalException.*;
import static com.example.masterplanbbe.domain.exam.entity.QExam.exam;
import static com.example.masterplanbbe.domain.exam.entity.QExamDetail.examDetail;
import static com.example.masterplanbbe.domain.spec.entity.QSpec.spec;
import static com.example.masterplanbbe.domain.specBookmark.entity.QSpecBookmark.specBookmark;

@Repository
@RequiredArgsConstructor
public class SpecRepositoryAdapter implements SpecRepositoryPort, SpecRepositoryCustom {
    private final SpecRepository specRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public CustomPage<SpecItemCardDto> getSpecItemCards(CustomPageRequest<SpecSortOption> request,
                                                        Long memberId) {
        OrderSpecifier<?> orderSpecifier = request.sort() != null ?
                SortUtil.getOrderSpecifier(request.sort(), request.isAsc()) :
                spec.createdAt.asc();

        List<SpecItemCardDto> list = queryFactory
                .select(new QSpecItemCardDto(
                        spec.name,
                        spec.category,
                        spec.participantCount,
                        exam.applyStartDate,
                        exam.applyEndDate,
                        exam.examStartDate,
                        specBookmark.isNotNull()
                ))
                .from(spec)
                .leftJoin(specBookmark)
                .on(specBookmark.spec.id.eq(spec.id).and(specBookmark.member.id.eq(memberId)))
                .leftJoin(exam)
                .on(exam.id.eq(spec.latestExam.id))
                .orderBy(orderSpecifier)
                .offset(request.getOffset())
                .limit(request.size())
                .fetch();

        LongSupplier countQuery = () -> Optional.ofNullable(
                        queryFactory
                                .select(spec.count())
                                .from(spec)
                                .fetchOne())
                .orElse(0L);

        return CustomPageUtils.getPage(list, request, countQuery);
    }

    @Override
    public SpecWithDetailsDto getSpecWithDetails(Long specId) {
        return queryFactory
                .select(
                        new QSpecWithDetailsDto(
                                spec.name,
                                spec.issuingOrganization,
                                spec.certificationType,
                                specBookmark.isNotNull(),
                                examDetail.preparation,
                                examDetail.eligibility,
                                examDetail.examStructure,
                                examDetail.passingCriteria
                        )
                )
                .from(spec)
                .leftJoin(specBookmark)
                .on(specBookmark.spec.id.eq(spec.id))
                .leftJoin(exam)
                .on(exam.id.eq(spec.latestExam.id))
                .leftJoin(examDetail)
                .on(examDetail.id.eq(exam.examDetail.id))
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
