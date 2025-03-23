package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.application.batch.dto.FinalCalculationWriteDTO;
import com.example.masterplanbbe.application.batch.dto.FinalJoinReadDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BatchFinalCalculationJdbcRepository {

    private static final String SELECT_SQL = """
            SELECT
                bacs.age_group,
                bacs.score,
                bacs.spec_id,
                jr.job_role_name,
                jr.category_id
            FROM batch_age_calculation_steps bacs
            JOIN job_role_specs jrs ON bacs.spec_id = jrs.spec_id
            STRAIGHT_JOIN job_roles jr ON jr.id = jrs.job_role_id
            LIMIT ? OFFSET ?""";

    private static final String DELETE_SQL = """
            TRUNCATE batch_final_calculation_steps""";

    private static final String INSERT_SQL = """
            INSERT INTO batch_final_calculation_steps
            (age_group, score, spec_id, job_role_name, category_id)
            VALUES (?, ?, ?, ?, ?)""";

    private final JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<FinalJoinReadDTO> find(int pageSize, int offset) {
        return jdbcTemplate.query(SELECT_SQL, (rs, rowNum) -> new FinalJoinReadDTO(
                rs.getString("age_group"),
                rs.getDouble("score"),
                rs.getLong("spec_id"),
                rs.getString("job_role_name"),
                rs.getLong("category_id")), pageSize, offset);
    }

    @Transactional
    public void batchSave(List<? extends FinalCalculationWriteDTO> data) {
        jdbcTemplate.batchUpdate(INSERT_SQL, data, data.size(), (ps, item) -> {
            ps.setString(1, item.ageGroup().getAge());
            ps.setDouble(2, item.score());
            ps.setLong(3, item.specId());
            ps.setString(4, item.jobRoleName());
            ps.setLong(5, item.categoryId());}
        );
    }

    @Transactional
    public void deleteAll() {
        jdbcTemplate.update(DELETE_SQL);
    }
}
