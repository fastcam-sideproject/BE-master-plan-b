package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.Recommendation;

import java.util.List;

public interface CustomRecommendationRepository {
    List<Recommendation> findALl();
}
