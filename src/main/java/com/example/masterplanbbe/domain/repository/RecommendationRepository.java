package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
}

