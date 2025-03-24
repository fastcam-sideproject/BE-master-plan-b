package com.example.masterplanbbe.presentation.response;

import com.example.masterplanbbe.domain.entity.MemberSpec;
import com.example.masterplanbbe.domain.entity.Spec;

import java.time.LocalDateTime;

public record MemberSpecResponse(
        String specName,
        String institution,
        Long score,
        Long specNumber,
        LocalDateTime achievementDate,
        LocalDateTime expiredDate
        ) {
    public static MemberSpecResponse from(MemberSpec memberSpec) {
        return new MemberSpecResponse(
                memberSpec.getSpec().getName(),
                memberSpec.getSpec().getIssuingOrganization(),
                memberSpec.getScore(),
                memberSpec.getSpecNumber(),
                memberSpec.getAchievementDate(),
                memberSpec.getExpiredDate()
        );
    }
}
