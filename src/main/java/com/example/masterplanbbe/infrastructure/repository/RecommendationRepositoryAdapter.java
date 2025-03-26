package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.application.dto.QRecommendationSpecDTO;
import com.example.masterplanbbe.application.dto.RecommendationSpecDTO;
import com.example.masterplanbbe.domain.entity.Category;
import com.example.masterplanbbe.domain.entity.JobRole;
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

    // 관심 직무가 없을 경우
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

    // 관심 직무 기반으로 탐색
    @Override
    public List<RecommendationSpecDTO> findByAgeGroupWithJobRoles(AgeGroup ageGroup, List<JobRole> jobRoles) {
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
                .where(recommendation.jobRole.in(jobRoles))
                .orderBy(recommendation.score.desc())
                .limit(6)
                .fetch();
    }

    // 형제 직무 기반으로 탐색
    @Override
    public List<RecommendationSpecDTO> findByAgeGroupAndNotJobRoles(AgeGroup ageGroup, List<JobRole> jobRoles, List<Category> categories) {
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
                .where(recommendation.jobRole.notIn(jobRoles))
                .where(recommendation.category.in(categories))
                .orderBy(recommendation.score.desc())
                .limit(6)
                .fetch();
    }

    // 직무에 속한 카테고리 이외의 나머지 탐색
    @Override
    public List<RecommendationSpecDTO> findByAgeGroupAndNotCategories(AgeGroup ageGroup, List<Category> categories) {
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
                .where(recommendation.category.notIn(categories))
                .orderBy(recommendation.score.desc())
                .limit(6)
                .fetch();
    }
}
