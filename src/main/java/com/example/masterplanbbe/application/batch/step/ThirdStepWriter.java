package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.RecommendationWriteDTO;
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
public class ThirdStepWriter implements ItemWriter<RecommendationWriteDTO> {

    private final BatchRecommendationJdbcRepository batchRecommendationJdbcRepository;

    @Override
    public void write(Chunk<? extends RecommendationWriteDTO> chunk) {
        // 데이터가 점차 많아지고, 신속성의 필요가 덜 중요시되고 안정성 입장에서
        // 스트림 정렬 메소드보다 우선순위 큐가 더 나을 것 같음
        // 우선순위 큐 기반 score 기준 내림차순 정렬
        PriorityQueue<RecommendationWriteDTO> sortedQueue =
                new PriorityQueue<>(Comparator
                        .comparing(RecommendationWriteDTO::score, Comparator.reverseOrder()));

        // 각 item을 PriorityQueue에 넣으면 자동으로 정렬됨
        sortedQueue.addAll(chunk.getItems());

        List<RecommendationWriteDTO> sortedList = new ArrayList<>(sortedQueue);
        batchRecommendationJdbcRepository.batchSave(sortedList);
    }
}
