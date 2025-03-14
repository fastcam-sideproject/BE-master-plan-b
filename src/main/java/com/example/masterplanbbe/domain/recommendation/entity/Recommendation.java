package com.example.masterplanbbe.domain.recommendation.entity;

import com.example.masterplanbbe.domain.spec.entity.Spec;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recommendations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "age_group", nullable = false)
    private AgeGroup ageGroup;

    @OneToOne
    @JoinColumn(name = "spec_id", nullable = false)
    private Spec spec;

    @Column(name = "old_score", nullable = false)
    private Double oldScore;

    @Column(name = "new_score", nullable = false)
    private Double newScore;
}
