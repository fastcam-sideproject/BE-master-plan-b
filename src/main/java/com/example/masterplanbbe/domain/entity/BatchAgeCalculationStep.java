package com.example.masterplanbbe.domain.entity;

import com.example.masterplanbbe.domain.enums.AgeGroup;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "batch_age_calculation_steps")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BatchAgeCalculationStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "age_group", nullable = false)
    private AgeGroup ageGroup;

    // 스펙 ID가 아니라 시험을 참조해야 되려나? 시험을 중심으로 인기있는 걸 추천하니까
    // 연산은 시험을 기준으로 좌르륵 전부 다 하고, 추천 데이터 적재는 스펙으로?
    @ManyToOne
    @JoinColumn(name = "spec_id", nullable = false)
    private Spec spec;

    @Column(name = "score", nullable = false)
    private Double score;
}
