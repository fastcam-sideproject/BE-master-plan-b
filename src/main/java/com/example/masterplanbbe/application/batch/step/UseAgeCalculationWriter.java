package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.UseAgeCalculationWriteDTO;
import com.example.masterplanbbe.infrastructure.repository.BatchUseAgeCalculationJdbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UseAgeCalculationWriter implements ItemWriter<UseAgeCalculationWriteDTO> {

    private static final int BATCH_SIZE = 30;

    private final BatchUseAgeCalculationJdbcRepository batchUseAgeCalculationJdbcRepository;

    @Override
    public void write(Chunk<? extends UseAgeCalculationWriteDTO> chunk) {
        batchUseAgeCalculationJdbcRepository.batchSave(chunk.getItems());
    }
}
