package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.RecommendationWriteDTO;
import com.example.masterplanbbe.infrastructure.repository.BatchRecommendationJdbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ThirdStepWriter implements ItemWriter<RecommendationWriteDTO> {

    private final BatchRecommendationJdbcRepository batchRecommendationJdbcRepository;

    @Override
    public void write(Chunk<? extends RecommendationWriteDTO> chunk) {
        batchRecommendationJdbcRepository.batchSave(chunk.getItems());
    }
}
