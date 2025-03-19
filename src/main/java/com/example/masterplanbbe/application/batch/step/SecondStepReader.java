package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.SecondStepReadDTO;
import com.example.masterplanbbe.infrastructure.repository.BatchIntermediateStepJdbcRepository;
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
public class SecondStepReader implements ItemReader<SecondStepReadDTO> {

    private final BatchIntermediateStepJdbcRepository batchIntermediateStepJdbcRepository;

    private List<SecondStepReadDTO> data;
    private int offset = 0;
    private int index = 0;

    @Override
    public SecondStepReadDTO read() throws
            UnexpectedInputException, ParseException, NonTransientResourceException {
        if (data == null || index >= data.size()) {
            int pageSize = 10;
            data = batchIntermediateStepJdbcRepository.findSecondView(pageSize, offset);

            if (data.isEmpty()) {
                log.info("step 2 null 반환");
                return null;
            }

            offset += pageSize;
            index = 0;
        }

        return data.get(index++);
    }
}
