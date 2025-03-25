package com.example.masterplanbbe.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BatchSortingRankJdbcRepository {

    private static final String SELECT_SQL = """
            SELECT 
                age_group,
                score,
                spec_id,
                job_role_id,
                category_id,
                ROW_NUMBER() OVER (PARTITION BY age_group, job_role_id ORDER BY score DESC) AS rank_main
            FROM batch_final_calculation_steps;
            """;

    private static final String INSERT_SQL = """
            INSERT INTO recommendations (age_group, score, spec_id, job_role_id, category_id, rank_main)
            VALUES (?, ?, ?, ?, ?, ?);
            """;

    private static final String DELETE_SQL = """
            TRUNCATE recommendations;""";

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void insert() {
        List<RankedSpec> rankedSpecs = jdbcTemplate.query(SELECT_SQL, (rs, rowNum) -> new RankedSpec(
                rs.getString("age_group"),
                rs.getDouble("score"),
                rs.getLong("spec_id"),
                rs.getLong("job_role_id"),
                rs.getLong("category_id"),
                rs.getInt("rank_main")
        ));

        List<Object[]> batchArgs = rankedSpecs.stream()
                .map(spec -> new Object[]{spec.ageGroup(), spec.score(), spec.specId(), spec.jobRoleId(), spec.categoryId(), spec.rankMain()})
                .toList();

        jdbcTemplate.batchUpdate(INSERT_SQL, batchArgs);
    }

    @Transactional
    public void deleteAll() {
        jdbcTemplate.update(DELETE_SQL);
    }

    private record RankedSpec(String ageGroup, double score, long specId, long jobRoleId, long categoryId, int rankMain) {
    }
}
