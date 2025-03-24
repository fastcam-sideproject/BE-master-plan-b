package com.example.masterplanbbe.presentation.request;

import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.entity.MemberSpec;
import com.example.masterplanbbe.domain.entity.Spec;

import java.time.LocalDateTime;

public record MemberSpecRequest(
        String specName,
        LocalDateTime achievementDate,
        LocalDateTime expiredDate,
        Long specNumber,
        Long score
) {
    public MemberSpec toEntity(Member member) {
        return new MemberSpec(
                member,
                specName(),
                score(),
                specNumber(),
                achievementDate(),
                expiredDate()
        );
    }
}
