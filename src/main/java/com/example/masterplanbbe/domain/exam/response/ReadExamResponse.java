package com.example.masterplanbbe.domain.exam.response;

public record ReadExamResponse(
        String title,
        String category,
        String authority,
        Double difficulty,
        Integer participantCount,
        String certificationType,
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {

}
