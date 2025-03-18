package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.application.batch.dto.FirstStepWriteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BatchFirstStepJdbcRepository {

    private static final String DELETE_SQL =
            "TRUNCATE batch_first_steps";
    private static final String INSERT_SQL =
            "INSERT INTO batch_first_steps (intermediate_result, latest_exam) VALUES (?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void batchSave(List<? extends FirstStepWriteDTO> data) {
        // 일괄 DELETE 후, INSERT 로 덮어쓰기 방식 구현
        jdbcTemplate.update(DELETE_SQL);
        jdbcTemplate.batchUpdate(INSERT_SQL, data, data.size(), (ps, item) -> {
            ps.setDouble(1, item.intermediateResult()); // intermediate_result
            ps.setLong(2, item.examId()); // latest_exam
        });
    }
}
