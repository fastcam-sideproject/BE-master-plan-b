package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.BatchAgeCalculationStep;

import java.util.List;

public interface CustomRecommendationRepository {
    List<BatchAgeCalculationStep> findALl();
}
