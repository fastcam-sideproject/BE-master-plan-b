package com.example.masterplanbbe.domain.exam.response;

import com.example.masterplanbbe.domain.exam.dto.SpecItemCardDto;
import org.springframework.data.domain.Page;

public record ReadAllSpecResponse(
        Page<SpecItemCardDto> specItemCardDtoPage
) {
}
