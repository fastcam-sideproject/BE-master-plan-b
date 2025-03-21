package com.example.masterplanbbe.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final JdbcTemplate jdbcTemplate;

    public Map<String, Map<String, List<Map<String, Object>>>> getStatisticsForBoth() {
        Map<String, List<Map<String, Object>>> 필기 = new LinkedHashMap<>();
        Map<String, List<Map<String, Object>>> 실기 = new LinkedHashMap<>();

        String sql = "CALL GetAllStatistics()";

        jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall(sql)) {
                boolean hasResults = cs.execute();

                String[] keys = {"difficulty", "studyMethod", "reflectionLevel", "timeSufficiency"};
                int index = 0;

                while (hasResults) {
                    try (ResultSet rs = cs.getResultSet()) {
                        List<Map<String, Object>> 필기List = new ArrayList<>();
                        List<Map<String, Object>> 실기List = new ArrayList<>();

                        while (rs.next()) {
                            String examType = rs.getString("exam_type"); // 필기/실기 구분

                            Map<String, Object> row = new HashMap<>();
                            ResultSetMetaData metaData = rs.getMetaData();
                            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                                String columnName = metaData.getColumnName(i);
                                if (!columnName.equals("exam_type")) { // `exam_type` 제거
                                    row.put(columnName, rs.getObject(i));
                                }
                            }

                            if ("필기".equals(examType)) {
                                필기List.add(row);
                            } else if ("실기".equals(examType)) {
                                실기List.add(row);
                            }
                        }
                        필기.put(keys[index], 필기List);
                        실기.put(keys[index], 실기List);
                    }
                    hasResults = cs.getMoreResults();
                    index++;
                }
            }
            return null;
        });

        Map<String, Map<String, List<Map<String, Object>>>> finalResults = new HashMap<>();
        finalResults.put("필기", 필기);
        finalResults.put("실기", 실기);
        return finalResults;
    }
}
