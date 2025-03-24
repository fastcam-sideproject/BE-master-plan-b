package com.example.masterplanbbe.presentation.response;

import com.example.masterplanbbe.domain.entity.MemberSpec;

import java.time.LocalDateTime;

public record MemberSpecResponse(
        String specName,
        String institution,
        Long score,
        Long specNumber,
        LocalDateTime achievementDate,
        LocalDateTime expiredDate
        ) {
    public static MemberSpecResponse from(MemberSpec spec) {
        return new MemberSpecResponse(
                spec.getSpec().getName(),
                spec.getSpec().getIssuingOrganization(),
                spec.getScore(),
                spec.getSpecNumber(),
                spec.getAchievementDate(),
                spec.getExpiredDate()
        );
    }
}
