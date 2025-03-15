package com.example.masterplanbbe.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_interest_jobs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJobRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "job_role_id", nullable = false)
    private JobRole jobRole;

    public MemberJobRole(Member member, JobRole jobRole) {
        this.member = member;
        this.jobRole = jobRole;
    }
}
