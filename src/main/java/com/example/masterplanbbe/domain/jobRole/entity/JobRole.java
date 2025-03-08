package com.example.masterplanbbe.domain.jobRole.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job_roles")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobRole extends FullAuditEntity {
    @Column(name = "job_role_name", nullable = false)
    private String jobRoleName;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
