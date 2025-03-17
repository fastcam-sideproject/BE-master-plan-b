package com.example.masterplanbbe.application.dto;

import com.example.masterplanbbe.domain.enums.AgeGroup;

public record RecommendationDTO(
        String specName,
        AgeGroup ageGroup,
        Double score
) implements Comparable<RecommendationDTO> {
    @Override
    public int compareTo(RecommendationDTO o) {
        // AgeGroup 비교 (ordinal() 값을 비교하여 나이가 어릴수록 우선)
        int ageComparison = Integer.compare(this.ageGroup.ordinal(), o.ageGroup.ordinal());

        // 만약 AgeGroup이 같다면 score 비교 (내림차순)
        return (ageComparison != 0) ? ageComparison : Double.compare(o.score, this.score);
    }
}
