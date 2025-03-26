package com.example.masterplanbbe.application.batch.dto;

public record GroupAgeReadDTO(
        Long specId, Long examId, String ageGroup, Double countSum) {
}
