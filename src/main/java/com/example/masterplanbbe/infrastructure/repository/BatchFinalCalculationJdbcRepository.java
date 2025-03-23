package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.application.batch.dto.JoinJobRoleReadDTO;
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

    private final JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<JoinJobRoleReadDTO> find(int pageSize, int offset) {
        return jdbcTemplate.query(SELECT_SQL, (rs, rowNum) -> new JoinJobRoleReadDTO(
                rs.getString("age_group"),
                rs.getDouble("score"),
                rs.getLong("spec_id"),
                rs.getString("job_role_name"),
                rs.getLong("category_id")), pageSize, offset);
    }
}
