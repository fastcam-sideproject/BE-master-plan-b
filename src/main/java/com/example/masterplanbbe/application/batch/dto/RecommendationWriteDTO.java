package com.example.masterplanbbe.application.batch.dto;

import com.example.masterplanbbe.domain.enums.AgeGroup;

public record RecommendationWriteDTO(AgeGroup ageGroup, Double score, Long specId) {
}
