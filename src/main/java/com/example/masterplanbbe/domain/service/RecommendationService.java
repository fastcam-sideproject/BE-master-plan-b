package com.example.masterplanbbe.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final JdbcTemplate jdbcTemplate;

    /**
     * 추천 테이블 신규 추천 점수 -> 구 추천 점수 필드로 업데이트
     */
    @Transactional
    public void updateOldScore() {
        String sql = "UPDATE recommendations SET old_score = new_score";
        jdbcTemplate.update(sql);
    }
}
