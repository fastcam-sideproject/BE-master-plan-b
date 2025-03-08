package com.example.masterplanbbe.domain.recommendation.dto;

import com.example.masterplanbbe.domain.recommendation.entity.AgeGroup;

import java.math.BigDecimal;

public record RecommendationDTO(
        String specName,
        AgeGroup ageGroup,
        BigDecimal oldScore,
        BigDecimal newScore
) implements Comparable<RecommendationDTO> {
    @Override
    public int compareTo(RecommendationDTO o) {
        return (o.newScore.subtract(o.oldScore)).compareTo(this.newScore.subtract(this.oldScore));
    }
}
