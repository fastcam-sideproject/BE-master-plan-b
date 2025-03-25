package com.example.masterplanbbe.domain.repository;

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

    // 1. 관심 직무에서 상위 추천 스펙 가져오기
    @Query("""
        SELECT r FROM Recommendation r
        WHERE r.ageGroup = :ageGroup
        AND r.jobRoleName IN :jobRoleNames
        ORDER BY r.score DESC
    """)
    List<Recommendation> findTopByJobRolesAndAge(
            @Param("jobRoleNames") List<String> jobRoleNames,
            @Param("ageGroup") AgeGroup ageGroup,
            Pageable pageable);  // Pageable을 사용하여 limit 적용

    // 2. 형제 직무(같은 category)에서 상위 추천 스펙 가져오기
    @Query("""
        SELECT r FROM Recommendation r
        WHERE r.ageGroup = :ageGroup
        AND r.category.id IN :categoryIds
        ORDER BY r.score DESC
    """)
    List<Recommendation> findTopByCategoryAndAge(
            @Param("categoryIds") List<Long> categoryIds,
            @Param("ageGroup") AgeGroup ageGroup,
            Pageable pageable);

    // 3. 전체 직무에서 남은 추천 스펙 가져오기
    @Query("""
        SELECT r FROM Recommendation r
        WHERE r.ageGroup = :ageGroup
        ORDER BY r.score DESC
    """)
    List<Recommendation> findTopByAge(
            @Param("ageGroup") AgeGroup ageGroup,
            Pageable pageable);
}

