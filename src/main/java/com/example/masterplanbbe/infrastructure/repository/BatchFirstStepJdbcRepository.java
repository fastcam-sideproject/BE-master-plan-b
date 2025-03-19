package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.application.batch.dto.FirstStepReadDTO;
import com.example.masterplanbbe.application.batch.dto.FirstStepWriteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BatchFirstStepJdbcRepository {

    // s.latest_exam과 ep.exam_id에 별개의 인덱스를 추가해보고 EXPLAIN ANALYZE 다시 해보자
    private static final String JOIN_SQL = """
            SELECT
                e.id AS exam_id,
                s.id AS spec_id,
                e.apply_end_date,
                e.exam_start_date,
                e.participant_count,
                ep.like_count,
                ep.view_count
            FROM specs s
            JOIN exams e ON s.latest_exam = e.id
            JOIN exam_posts ep ON e.id = ep.exam_id
            LIMIT ? OFFSET ?""";

    private static final String DELETE_SQL =
            "TRUNCATE batch_first_steps";
    private static final String INSERT_SQL =
            "INSERT INTO batch_first_steps (intermediate_result, latest_exam_id, spec_id) VALUES (?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<FirstStepReadDTO> find(int pageSize, int offset) {
        return jdbcTemplate.query(JOIN_SQL, (rs, rowNum) -> new FirstStepReadDTO(
                rs.getLong("spec_id"),
                rs.getLong("exam_id"),
                rs.getDate("apply_end_date").toLocalDate(),
                rs.getDate("exam_start_date").toLocalDate(),
                rs.getInt("participant_count"),
                rs.getInt("like_count"),
                rs.getInt("view_count")), pageSize, offset);
    }

    // 일괄 DELETE 후, INSERT 로 덮어쓰기 방식 구현
    @Transactional
    public void batchSave(List<? extends FirstStepWriteDTO> data) {
        jdbcTemplate.batchUpdate(INSERT_SQL, data, data.size(), (ps, item) -> {
            ps.setDouble(1, item.intermediateResult()); // intermediate_result
            ps.setLong(2, item.examId()); // latest_exam
            ps.setLong(3, item.specId()); // spec
        });
    }

    @Transactional
    public void deleteAll() {
        jdbcTemplate.update(DELETE_SQL);
    }
}
