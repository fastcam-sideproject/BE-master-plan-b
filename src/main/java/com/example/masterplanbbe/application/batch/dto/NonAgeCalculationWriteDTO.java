package com.example.masterplanbbe.application.batch.dto;

public record NonAgeCalculationWriteDTO(
        Long specId,
        String specName,
        Long examId,
        Double intermediateResult) {
}
