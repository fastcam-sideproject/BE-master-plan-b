package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.NonAgeCalculationWriteDTO;
import com.example.masterplanbbe.infrastructure.repository.BatchIntermediateStepJdbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecondStepWriter implements ItemWriter<NonAgeCalculationWriteDTO> {

    private final BatchIntermediateStepJdbcRepository batchIntermediateStepJdbcRepository;

    @Override
    public void write(Chunk<? extends NonAgeCalculationWriteDTO> chunk) {
        batchIntermediateStepJdbcRepository.batchUpdate(chunk.getItems());
    }
}
