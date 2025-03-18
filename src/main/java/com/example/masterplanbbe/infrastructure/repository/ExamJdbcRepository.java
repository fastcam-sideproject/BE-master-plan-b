package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.application.batch.dto.FirstStepReadDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExamJdbcRepository {

    private static final String SQL = "SELECT \n" +
            "    e.id AS exam_id, \n" +
            "    e.apply_end_date, \n" +
            "    e.exam_start_date, \n" +
            "    e.participant_count\n" +
            "FROM specs s\n" +
            "JOIN exams e ON s.latest_exam = e.id\n" +
            "LIMIT ? OFFSET ?";

    private final JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<FirstStepReadDTO> find(int pageSize, int offset) {
        return jdbcTemplate.query(SQL, (rs, rowNum) -> new FirstStepReadDTO(
                rs.getLong("exam_id"),
                rs.getDate("apply_end_date").toLocalDate(),
                rs.getDate("exam_start_date").toLocalDate(),
                rs.getInt("participant_count")), pageSize, offset);
    }
}
