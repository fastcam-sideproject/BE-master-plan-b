package com.example.masterplanbbe.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BatchSecondStepJdbcRepository {

    private static final String GROUP_JOIN_SQL = """
            SELECT
                ep.exam_id  AS exam_id,
                s.id AS spec_id,
                SUM(ep.like_count) AS total_like_count,  -- like_count 합산
                SUM(ep.view_count) AS total_view_count  -- view_count 합산
            FROM specs s
            JOIN exam_posts ep ON s.latest_exam = ep.exam_id
            GROUP BY ep.exam_id , s.id;
            """;

    private final JdbcTemplate jdbcTemplate;

}
