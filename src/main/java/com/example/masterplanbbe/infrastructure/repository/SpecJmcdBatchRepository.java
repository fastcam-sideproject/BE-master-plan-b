package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.presentation.response.JmcdApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SpecJmcdBatchRepository {
    private final JdbcTemplate jdbcTemplate;

    public void batchUpsert(List<JmcdApiResponse.Item> items) {
        if (items.isEmpty()) {
            return;
        }

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO spec_jmcds (spec_id, spec_name, jmcd) VALUES ");

        List<Object> params = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            sql.append("(?, ?, ?)");
            if (i != items.size() - 1) {
                sql.append(", ");
            }

            JmcdApiResponse.Item item = items.get(i);
            params.add(null);
            params.add(item.specName());
            params.add(item.jmcd());
        }

        sql.append(" ON DUPLICATE KEY UPDATE jmcd = VALUES(jmcd)");

        jdbcTemplate.update(sql.toString(), params.toArray());
    }
}
