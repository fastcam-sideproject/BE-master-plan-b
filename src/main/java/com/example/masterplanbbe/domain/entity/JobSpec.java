package com.example.masterplanbbe.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job_specs")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobSpec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_role_id", nullable = false)
    private JobRole jobRole;

    @ManyToOne
    @JoinColumn(name = "spec_id", nullable = false)
    private Spec spec;

    public JobSpec(JobRole jobRole, Spec spec) {
        this.jobRole = jobRole;
        this.spec = spec;
    }
}
