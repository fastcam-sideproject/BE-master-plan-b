package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.FinalJoinReadDTO;
import com.example.masterplanbbe.infrastructure.repository.BatchFinalCalculationJdbcRepository;
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
public class FinalCalculationReader implements ItemReader<FinalJoinReadDTO> {

    private final BatchFinalCalculationJdbcRepository batchFinalCalculationJdbcRepository;

    private List<FinalJoinReadDTO> data;
    private int offset = 0;
    private int index = 0;

    @Override
    public FinalJoinReadDTO read() throws
            UnexpectedInputException, ParseException, NonTransientResourceException {
        if (data == null || index >= data.size()) {
            int pageSize = 10;
            data = batchFinalCalculationJdbcRepository.find(pageSize, offset);

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
