package com.example.masterplanbbe.application.batch.dto;

public record GroupAgeReadDTO(
        Long specId, String specName, Long examId, String ageGroup, Double countSum) {
}
