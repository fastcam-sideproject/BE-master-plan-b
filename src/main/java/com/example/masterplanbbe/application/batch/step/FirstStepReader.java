package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.FirstStepReadDTO;
import com.example.masterplanbbe.infrastructure.repository.ExamJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FirstStepReader implements ItemReader<FirstStepReadDTO> {

    private final ExamJdbcRepository examJdbcRepository;

    private List<FirstStepReadDTO> data;
    private int offset = 0;
    private int index = 0;

    @Override
    public FirstStepReadDTO read() throws
            UnexpectedInputException, ParseException, NonTransientResourceException {
        if (data == null || index >= data.size()) {
            int pageSize = 10;
            data = examJdbcRepository.find(pageSize, offset);

            if (data.size() % pageSize == 0) return null;

            offset += pageSize;
            index = 0;
        }

        return data.get(index++);
    }
}
