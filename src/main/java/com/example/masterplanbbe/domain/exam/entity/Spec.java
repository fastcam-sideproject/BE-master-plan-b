package com.example.masterplanbbe.domain.exam.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "specs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Spec extends FullAuditEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String issuingOrganization;

    @Enumerated(EnumType.STRING)
    private CertificationType certificationType;

    @OneToMany(mappedBy = "spec")
    private List<Subject> subject;

}
