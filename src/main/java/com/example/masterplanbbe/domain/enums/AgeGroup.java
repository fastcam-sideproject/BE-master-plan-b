package com.example.masterplanbbe.domain.enums;

import lombok.Getter;

@Getter
public enum AgeGroup {
    EARLY_20S(Age.EARLY_20S),
    MID_20S(Age.MID_20S),
    LATE_20S(Age.LATE_20S),
    OVER_30S(Age.OVER_30S),;

    private final String age;

    AgeGroup(String age) {
        this.age = age;
    }

    public static class Age {
        public static final String EARLY_20S = "EARLY_20S";
        public static final String MID_20S = "MID_20S";
        public static final String LATE_20S = "LATE_20S";
        public static final String OVER_30S = "OVER_30S";
    }
}
