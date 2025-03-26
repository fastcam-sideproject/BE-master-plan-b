package com.example.masterplanbbe.presentation.request;

import java.time.LocalDate;

public record AgeUpdateRequest(
        LocalDate birthDate
) {
}
