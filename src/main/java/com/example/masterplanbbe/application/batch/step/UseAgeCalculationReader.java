package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.ThirdStepReadDTO;
import com.example.masterplanbbe.infrastructure.repository.BatchRecommendationJdbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UseAgeCalculationReader implements ItemReader<ThirdStepReadDTO> {

    private final BatchRecommendationJdbcRepository batchRecommendationJdbcRepository;

    private List<ThirdStepReadDTO> data;
    private int offset = 0;
    private int index = 0;

    @Override
    public ThirdStepReadDTO read() throws
            UnexpectedInputException, ParseException, NonTransientResourceException {
        if (data == null || index >= data.size()) {
            int pageSize = 10;
            data = batchRecommendationJdbcRepository.find(pageSize, offset);

            if (data.isEmpty()) {
                log.info("step 3 null 반환");
                return null;
            }

            offset += pageSize;
            index = 0;
        }

        return data.get(index++);
    }
}
