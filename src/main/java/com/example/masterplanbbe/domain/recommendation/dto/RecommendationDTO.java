package com.example.masterplanbbe.domain.recommendation.dto;

import com.example.masterplanbbe.domain.recommendation.entity.AgeGroup;

public record RecommendationDTO(
        String specName,
        AgeGroup ageGroup,
        Double oldScore,
        Double newScore
) implements Comparable<RecommendationDTO> {
    @Override
    public int compareTo(RecommendationDTO o) {
        double thisDifference = this.newScore - this.oldScore;
        double otherDifference = o.newScore - o.oldScore;

        return Double.compare(thisDifference, otherDifference);
    }
}
