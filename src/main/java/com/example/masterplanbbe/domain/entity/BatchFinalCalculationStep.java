package com.example.masterplanbbe.domain.entity;

import com.example.masterplanbbe.domain.enums.AgeGroup;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "batch_final_calculation_steps")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BatchFinalCalculationStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "age_group", nullable = false)
    private AgeGroup ageGroup;

    @Column(name = "score", nullable = false)
    private Double score;

    @ManyToOne
    @JoinColumn(name = "spec_id", nullable = false)
    private Spec spec;

    @Column(name = "job_role_name", nullable = false)
    private String jobRoleName;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
