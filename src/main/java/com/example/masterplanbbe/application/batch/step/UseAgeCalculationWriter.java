package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.UseAgeCalculationWriteDTO;
import com.example.masterplanbbe.infrastructure.repository.BatchRecommendationJdbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Slf4j
@Component
@RequiredArgsConstructor
public class UseAgeCalculationWriter implements ItemWriter<UseAgeCalculationWriteDTO> {

    private static final int BATCH_SIZE = 30;

    private final BatchRecommendationJdbcRepository batchRecommendationJdbcRepository;

    @Override
    public void write(Chunk<? extends UseAgeCalculationWriteDTO> chunk) {
        batchRecommendationJdbcRepository.batchSave(chunk.getItems());
    }
}
