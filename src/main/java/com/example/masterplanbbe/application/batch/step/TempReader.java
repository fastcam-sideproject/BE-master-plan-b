package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.TempDTO;
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
public class TempReader implements ItemReader<TempDTO> {

    private final BatchIntermediateStepJdbcRepository batchIntermediateStepJdbcRepository;

    private List<TempDTO> data;
    private int offset = 0;
    private int index = 0;

    @Override
    public TempDTO read() throws
            UnexpectedInputException, ParseException, NonTransientResourceException {
        if (data == null || index >= data.size()) {
            int pageSize = 10;
            data = batchIntermediateStepJdbcRepository.findView(pageSize, offset);

            if (data.isEmpty()) {
                log.info("step 1 null 반환");
                return null;
            }

            offset += pageSize;
            index = 0;
        }

        return data.get(index++);
    }
}
