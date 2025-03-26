package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.application.dto.RecommendationSpecDTO;
import com.example.masterplanbbe.domain.enums.AgeGroup;

import java.util.List;

public interface RecommendationRepositoryCustom {
    List<RecommendationSpecDTO> findByAgeGroupWithoutJobRoles(AgeGroup ageGroup);
}
