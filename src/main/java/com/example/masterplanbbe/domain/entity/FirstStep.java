package com.example.masterplanbbe.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "batch_first_steps")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FirstStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "latest_exam")
    private Exam latestExam; // 해당 스펙에 대응되는 최신 시험

    @Column(name = "intermediate_result")
    private Double intermediateResult; // 연령대와 무관한 파라미터 기반 중간 추천 점수
}
