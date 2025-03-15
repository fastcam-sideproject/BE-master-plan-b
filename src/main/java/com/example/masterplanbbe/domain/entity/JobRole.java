package com.example.masterplanbbe.domain.entity;

import com.example.masterplanbbe.domain.common.FullAuditEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "jobrole", cascade = CascadeType.ALL)
    private Set<Spec> specs = new HashSet<>();

    @OneToMany(mappedBy = "jobRole", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberJobRole> memberJobRoles = new HashSet<>();

    public void addSpec(Spec spec) {
        specs.add(spec);
        spec.setJobRole(this);
    }

    public void removeSpec(Spec spec) {
        specs.remove(spec);
        spec.setJobRole(null);
    }
}
