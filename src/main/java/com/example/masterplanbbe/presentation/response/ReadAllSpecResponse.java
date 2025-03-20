package com.example.masterplanbbe.presentation.response;

import com.example.masterplanbbe.application.dto.SpecItemCardDto;
import org.springframework.data.domain.Page;

public record ReadAllSpecResponse(
        Page<SpecItemCardDto> specItemCardDtoPage
) {
}
