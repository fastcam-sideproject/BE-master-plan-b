package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.FirstStepWriteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FirstStepWriter implements ItemWriter<FirstStepWriteDTO> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void write(Chunk<? extends FirstStepWriteDTO> chunk) throws Exception {

    }
}
