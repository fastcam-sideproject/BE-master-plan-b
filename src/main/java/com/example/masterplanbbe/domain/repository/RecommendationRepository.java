package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.Category;
import com.example.masterplanbbe.domain.entity.JobRole;
import com.example.masterplanbbe.domain.entity.Recommendation;
import com.example.masterplanbbe.domain.entity.Spec;
import com.example.masterplanbbe.domain.enums.AgeGroup;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    // 1. 관심 직무 기반 추천
    @Query("""
        SELECT r FROM Recommendation r
        LEFT JOIN FETCH r.spec rs
        LEFT JOIN FETCH rs.latestExam
        WHERE r.ageGroup = :ageGroup
        AND r.jobRole IN :jobRoles
        ORDER BY r.score DESC
    """)
    List<Recommendation> findTopByJobRolesAndAge(
            @Param("jobRoles") List<JobRole> jobRoles,
            @Param("ageGroup") AgeGroup ageGroup,
            Pageable pageable);

    // 2. 형제 카테고리 기반 추천 (직접 선택한 직무 제외)
    @Query("""
        SELECT r FROM Recommendation r
        LEFT JOIN FETCH r.spec rs
        LEFT JOIN FETCH rs.latestExam
        WHERE r.ageGroup = :ageGroup
        AND r.category IN :categories
        AND r.jobRole NOT IN :jobRoles
        ORDER BY r.score DESC
    """)
    List<Recommendation> findTopByCategoryAndAge(
            @Param("jobRoles") List<JobRole> jobRoles,
            @Param("categories") List<Category> categories,
            @Param("ageGroup") AgeGroup ageGroup,
            Pageable pageable);

    // 3. 전체 연령대 추천 (선택한 카테고리는 제외)
    @Query("""
        SELECT r FROM Recommendation r
        LEFT JOIN FETCH r.spec rs
        LEFT JOIN FETCH rs.latestExam
        WHERE r.ageGroup = :ageGroup
        AND r.category NOT IN :categories
        ORDER BY r.score DESC
    """)
    List<Recommendation> findTopByAge(
            @Param("categories") List<Category> categories,
            @Param("ageGroup") AgeGroup ageGroup,
            Pageable pageable);

    // 추가: jobRoles와 categories가 비어 있을 경우 대비
    @Query("""
        SELECT r FROM Recommendation r
        LEFT JOIN FETCH r.spec rs
        LEFT JOIN FETCH rs.latestExam
        WHERE r.ageGroup = :ageGroup
        ORDER BY r.score DESC
    """)
    List<Recommendation> findTopByAgeWithoutCategory(
            @Param("ageGroup") AgeGroup ageGroup,
            Pageable pageable);
}

