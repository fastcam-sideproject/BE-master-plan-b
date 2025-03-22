package com.example.masterplanbbe.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "batch_calculation_steps")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BatchCalculationStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "latest_exam_id")
    private Exam latestExam; // 해당 스펙에 대응되는 최신 시험

    @OneToOne
    @JoinColumn(name = "spec_id")
    private Spec spec; // 해당 시험에 대응되는 스펙

    @Column(name = "spec_name")
    private String specName;

    @Column(name = "intermediate_result")
    private Double intermediateResult; // 연령대와 무관한 파라미터 기반 중간 추천 점수
}
