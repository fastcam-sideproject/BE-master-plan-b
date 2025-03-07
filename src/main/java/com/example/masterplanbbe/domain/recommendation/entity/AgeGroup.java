package com.example.masterplanbbe.domain.recommendation.entity;

import java.time.LocalDate;

public enum AgeGroup {
    EARLY_20S,
    MID_20S,
    LATE_20S,
    OVER_30S;

    public static AgeGroup getAgeGroup(LocalDate birthDate) {
        int birthYear = birthDate.getYear();
        int nowYear = LocalDate.now().getYear();
        int age = nowYear - birthYear;

        if (age >= 24 && age <= 26) { // 24세부터 26세
            return MID_20S;
        } else if (age >= 27 && age <= 29) { // 27세부터 29세
            return LATE_20S;
        } else if (age >= 30) { // 30세 이상
            return OVER_30S;
        }

        return EARLY_20S; // 23세 이하
    }
}
