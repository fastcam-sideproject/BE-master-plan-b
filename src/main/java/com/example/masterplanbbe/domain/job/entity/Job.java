package com.example.masterplanbbe.domain.job.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jobs")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Job extends FullAuditEntity {
    @Column(name = "job_name", nullable = false)
    private String jobName;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
