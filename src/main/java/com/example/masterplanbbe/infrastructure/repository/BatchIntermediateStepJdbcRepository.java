package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.application.batch.dto.FirstStepReadDTO;
import com.example.masterplanbbe.application.batch.dto.NonAgeCalculationWriteDTO;
import com.example.masterplanbbe.application.batch.dto.SecondStepReadDTO;
import com.example.masterplanbbe.application.batch.dto.PreCalculationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BatchIntermediateStepJdbcRepository {

    // s.latest_exam과 ep.exam_id에 별개의 인덱스를 추가해보고 EXPLAIN ANALYZE 다시 해보자
    private static final String JOIN_SQL = """
            SELECT
                e.id AS exam_id,
                s.id AS spec_id,
                e.apply_end_date,
                e.exam_start_date,
                e.participant_count
            FROM specs s
            JOIN exams e ON s.latest_exam = e.id
            LIMIT ? OFFSET ?""";

    private static final String GROUP_JOIN_SQL = """
            SELECT
                ep.exam_id  AS exam_id,
                s.id AS spec_id,
                SUM(ep.like_count) AS total_like_count,  -- like_count 합산
                SUM(ep.view_count) AS total_view_count  -- view_count 합산
            FROM specs s
            JOIN exam_posts ep ON s.latest_exam = ep.exam_id
            GROUP BY ep.exam_id , s.id
            LIMIT ? OFFSET ?""";

    // 1, 2 스텝을 하나로 묶어서 사전 순서 정렬을 해보자
    // 혹시나 모를 NULL 대응을 위한 COALESCE 적용
    private static final String SUB_QUERY_JOIN_SQL = """
            SELECT
                j.exam_id,
                j.spec_id,
                j.apply_end_date,
                j.exam_start_date,
                j.participant_count,
                COALESCE(SUM(ep.like_count), 0) AS total_like_count,
                COALESCE(SUM(ep.view_count), 0) AS total_view_count
            FROM (
                SELECT
                    e.id AS exam_id,
                    s.id AS spec_id,
                    e.apply_end_date,
                    e.exam_start_date,
                    e.participant_count
                FROM specs s
                JOIN exams e ON s.latest_exam = e.id
                LIMIT ? OFFSET ?
            ) AS j
            LEFT JOIN exam_posts ep ON j.exam_id = ep.exam_id
            GROUP BY j.exam_id, j.spec_id, j.apply_end_date, j.exam_start_date, j.participant_count
            LIMIT ? OFFSET ?""";

    private static final String DELETE_SQL = "TRUNCATE batch_calculation_steps";

    private static final String INSERT_SQL = """
            INSERT INTO batch_calculation_steps
            (intermediate_result, latest_exam_id, spec_id)
            VALUES (?, ?, ?)""";

    private static final String UPDATE_SQL = """
            UPDATE batch_calculation_steps
            SET intermediate_result = intermediate_result + ?
            WHERE latest_exam_id = ? AND spec_id = ?""";

    private final JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<PreCalculationDTO> findView(int pageSize, int offset) {
        return jdbcTemplate.query(SUB_QUERY_JOIN_SQL, (rs, rowNum) -> new PreCalculationDTO(
                rs.getLong("exam_id"),
                rs.getLong("spec_id"),
                rs.getDate("apply_end_date").toLocalDate(),
                rs.getDate("exam_start_date").toLocalDate(),
                rs.getInt("participant_count"),
                rs.getInt("total_like_count"),
                rs.getInt("total_view_count")), pageSize, offset);
    }

    // 일괄 DELETE 후, INSERT 로 덮어쓰기 방식 구현
    @Transactional
    public void batchSave(List<? extends NonAgeCalculationWriteDTO> data) {
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
