package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.IntermediateStepWriteDTO;
import com.example.masterplanbbe.infrastructure.repository.BatchFirstStepJdbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FirstStepWriter implements ItemWriter<IntermediateStepWriteDTO> {

    private final BatchFirstStepJdbcRepository batchFirstStepJdbcRepository;

    @Override
    public void write(Chunk<? extends IntermediateStepWriteDTO> chunk) {
//        log.info("FirstStepWriter 데이터 : {}", chunk.getItems());
        batchFirstStepJdbcRepository.batchSave(chunk.getItems());
    }
}
