package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.FinalCalculationWriteDTO;
import com.example.masterplanbbe.infrastructure.repository.BatchFinalCalculationJdbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FinalCalculationWriter implements ItemWriter<FinalCalculationWriteDTO> {

    private final BatchFinalCalculationJdbcRepository batchFinalCalculationJdbcRepository;

    @Override
    public void write(Chunk<? extends FinalCalculationWriteDTO> chunk) throws Exception {
        batchFinalCalculationJdbcRepository.batchSave(chunk.getItems());
    }
}
