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
public class ThirdStepWriter implements ItemWriter<UseAgeCalculationWriteDTO> {

    private static final int BATCH_SIZE = 30;

    private final BatchRecommendationJdbcRepository batchRecommendationJdbcRepository;

    @Override
    public void write(Chunk<? extends UseAgeCalculationWriteDTO> chunk) {
        // 데이터가 점차 많아지고, 신속성의 필요가 덜 중요시되고 안정성 입장에서
        // 스트림 정렬 메소드보다 우선순위 큐가 더 나을 것 같음
        // 우선순위 큐 기반 score 기준 내림차순 정렬
        PriorityQueue<UseAgeCalculationWriteDTO> queue =
                new PriorityQueue<>(Comparator
                        .comparing(UseAgeCalculationWriteDTO::score, Comparator.reverseOrder()));

        List<UseAgeCalculationWriteDTO> batchList = new ArrayList<>();

        while (!queue.isEmpty()) {
            batchList.add(queue.poll());

            // 일정 크기(BATCH_SIZE) 이상이면 batch 저장 후 리스트 초기화
            if (batchList.size() >= BATCH_SIZE) {
                batchRecommendationJdbcRepository.batchSave(batchList);
                batchList.clear();
            }
        }

        // 남은 데이터 저장
        if (!batchList.isEmpty()) {
            batchRecommendationJdbcRepository.batchSave(batchList);
        }
    }
}
