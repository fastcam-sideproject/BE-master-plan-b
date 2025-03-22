package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.application.batch.dto.GroupAgeReadDTO;
import com.example.masterplanbbe.application.batch.dto.UseAgeCalculationWriteDTO;
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
                bss.spec_name AS spec_name,
                bss.latest_exam_id AS exam_id,
                CASE
                    WHEN TIMESTAMPDIFF(YEAR, m.birthdate, CURDATE()) <= 23 THEN 'EARLY_20S'
                    WHEN TIMESTAMPDIFF(YEAR, m.birthdate, CURDATE()) BETWEEN 24 AND 26 THEN 'MID_20S'
                    WHEN TIMESTAMPDIFF(YEAR, m.birthdate, CURDATE()) BETWEEN 27 AND 29 THEN 'LATE_20S'
                    ELSE 'OVER_30S'
                END AS age_group,
                COUNT(*) * 0.1 + bss.intermediate_result AS count_sum
            FROM batch_calculation_steps bss
            JOIN exam_posts ep ON bss.latest_exam_id = ep.exam_id
            JOIN members m ON ep.member_id = m.id
            GROUP BY bss.spec_id, bss.latest_exam_id, age_group
            LIMIT ? OFFSET ?""";

    private static final String DELETE_SQL = "TRUNCATE batch_age_calculation_steps;";

    private static final String INSERT_SQL = """
            INSERT INTO batch_age_calculation_steps (age_group, score, spec_id, spec_name)
            VALUES (?, ?, ?, ?)""";

    private final JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<GroupAgeReadDTO> find(int pageSize, int offset) {
        return jdbcTemplate.query(GROUP_SUM_SQL, (rs, rowNum) -> new GroupAgeReadDTO(
                rs.getLong("spec_id"),
                rs.getString("spec_name"),
                rs.getLong("exam_id"),
                rs.getString("age_group"),
                rs.getDouble("count_sum")), pageSize, offset);
    }

    @Transactional
    public void batchSave(List<? extends UseAgeCalculationWriteDTO> data) {
        jdbcTemplate.batchUpdate(INSERT_SQL, data, data.size(), (ps, item) -> {
            ps.setString(1, item.ageGroup().getAge()); // age
            ps.setDouble(2, item.score()); // score
            ps.setLong(3, item.specId()); // spec
            ps.setString(4, item.specName());
        });
    }

    @Transactional
    public void deleteAll() {
        jdbcTemplate.update(DELETE_SQL);
    }
}
