package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.application.dto.QRecommendationSpecDTO;
import com.example.masterplanbbe.application.dto.RecommendationSpecDTO;
import com.example.masterplanbbe.domain.enums.AgeGroup;
import com.example.masterplanbbe.domain.repository.RecommendationRepository;
import com.example.masterplanbbe.domain.repository.RecommendationRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.masterplanbbe.domain.entity.QRecommendation.recommendation;
import static com.example.masterplanbbe.domain.entity.QSpec.spec;
import static com.example.masterplanbbe.domain.entity.QExam.exam;

@Repository
@RequiredArgsConstructor
public class RecommendationRepositoryAdapter implements RecommendationRepositoryCustom {

    private final RecommendationRepository recommendationRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<RecommendationSpecDTO> findByAgeGroupWithoutJobRoles(AgeGroup ageGroup) {
        return queryFactory.select(
            new QRecommendationSpecDTO(
                    spec.id,
                    spec.name,
                    spec.certificationType,
                    exam.applyStartDate,
                    exam.applyEndDate,
                    exam.examStartDate))
                .from(recommendation)
                .join(spec).on(recommendation.spec.id.eq(spec.id))
                .join(exam).on(spec.latestExam.id.eq(exam.id))
                .where(recommendation.ageGroup.eq(ageGroup))
                .orderBy(recommendation.score.desc())
                .limit(6)
                .fetch();
    }
}
