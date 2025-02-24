package com.example.masterplanbbe.domain.spec.response;

import com.example.masterplanbbe.domain.spec.dto.SpecItemCardDto;
import org.springframework.data.domain.Page;

public record ReadAllSpecResponse(
        Page<SpecItemCardDto> specItemCardDtoPage
) {
}
