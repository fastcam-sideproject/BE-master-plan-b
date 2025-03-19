package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.application.batch.dto.RecommendationReadDTO;
import com.example.masterplanbbe.application.batch.dto.RecommendationWriteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BatchRecommendationJdbcRepository {

    private static final String GROUP_SUM_SQL = """
            SELECT
                bss.spec_id AS spec_id,
                bss.latest_exam_id AS exam_id,
                CASE
                    WHEN TIMESTAMPDIFF(YEAR, m.birthdate, CURDATE()) <= 23 THEN 'EARLY_20S'
                    WHEN TIMESTAMPDIFF(YEAR, m.birthdate, CURDATE()) BETWEEN 24 AND 26 THEN 'MID_20S'
                    WHEN TIMESTAMPDIFF(YEAR, m.birthdate, CURDATE()) BETWEEN 27 AND 29 THEN 'LATE_20S'
                    ELSE 'OVER_30S'
                END AS age_group,
                COUNT(*) AS count_sum
            FROM batch_calculation_steps bss
            JOIN exam_posts ep ON bss.latest_exam_id = ep.exam_id
            JOIN members m ON ep.member_id = m.id
            GROUP BY bss.spec_id, bss.latest_exam_id, age_group
            LIMIT ? OFFSET ?""";
    private static final String DELETE_SQL = "TRUNCATE recommendations;";
    private static final String INSERT_SQL =
            "INSERT INTO recommendations (age_group, score, spec_id) VALUES (?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<RecommendationReadDTO> find(int pageSize, int offset) {
        return jdbcTemplate.query(GROUP_SUM_SQL, (rs, rowNum) -> new RecommendationReadDTO(
                rs.getLong("spec_id"),
                rs.getLong("exam_id"),
                rs.getString("age_group"),
                rs.getInt("count_sum")), pageSize, offset);
    }

    @Transactional
    public void batchSave(List<? extends RecommendationWriteDTO> data) {
        jdbcTemplate.batchUpdate(INSERT_SQL, data, data.size(), (ps, item) -> {
            ps.setString(1, item.ageGroup().getAge()); // age
            ps.setDouble(2, item.score()); // score
            ps.setLong(3, item.specId()); // spec
        });
    }

    @Transactional
    public void deleteAll() {
        jdbcTemplate.update(DELETE_SQL);
    }
}
