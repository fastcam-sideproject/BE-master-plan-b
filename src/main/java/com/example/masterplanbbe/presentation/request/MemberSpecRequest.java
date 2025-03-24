package com.example.masterplanbbe.presentation.request;

import java.time.LocalDateTime;

public record MemberSpecRequest(
        String specName,
        Long score,
        Long specNumber,
        LocalDateTime achievementDate,
        LocalDateTime expiredDate
) {
}
