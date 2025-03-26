package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.application.dto.RecommendationSpecDTO;
import com.example.masterplanbbe.domain.entity.Category;
import com.example.masterplanbbe.domain.entity.JobRole;
import com.example.masterplanbbe.domain.enums.AgeGroup;

import java.util.List;

public interface RecommendationRepositoryCustom {
    List<RecommendationSpecDTO> findByAgeGroupWithoutJobRoles(AgeGroup ageGroup);
    List<RecommendationSpecDTO> findByAgeGroupWithJobRoles(AgeGroup ageGroup, List<JobRole> jobRoles);
    List<RecommendationSpecDTO> findByAgeGroupAndNotJobRoles(AgeGroup ageGroup, List<JobRole> jobRoles, List<Category> categories);
    List<RecommendationSpecDTO> findByAgeGroupAndNotCategories(AgeGroup ageGroup, List<Category> categories);
}
