package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.BatchAgeCalculationStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository extends JpaRepository<BatchAgeCalculationStep, Long> {
    // 조회에 있어서의 정렬???
    // 파라미터에 관심 직무가 있을 때, 없을 때... 흠
}
