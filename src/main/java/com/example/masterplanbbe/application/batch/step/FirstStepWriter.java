package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.NonAgeCalculationWriteDTO;
import com.example.masterplanbbe.infrastructure.repository.BatchIntermediateStepJdbcRepository;
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
public class FirstStepWriter implements ItemWriter<NonAgeCalculationWriteDTO> {

    private final BatchIntermediateStepJdbcRepository batchIntermediateStepJdbcRepository;

    @Override
    public void write(Chunk<? extends NonAgeCalculationWriteDTO> chunk) {
//        log.info("FirstStepWriter 데이터 : {}", chunk.getItems());
        PriorityQueue<NonAgeCalculationWriteDTO> sortedQueue =
                new PriorityQueue<>(Comparator
                        .comparing(NonAgeCalculationWriteDTO::intermediateResult,
                                Comparator.reverseOrder()));

        sortedQueue.addAll(chunk.getItems());

        List<NonAgeCalculationWriteDTO> sortedList = new ArrayList<>(sortedQueue);
        batchIntermediateStepJdbcRepository.batchSave(sortedList);
    }
}
