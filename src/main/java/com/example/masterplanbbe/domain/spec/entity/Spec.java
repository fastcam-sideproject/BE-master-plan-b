package com.example.masterplanbbe.domain.spec.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "specs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Spec extends FullAuditEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String issuingOrganization;

    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    private CertificationType certificationType;

    @Column(nullable = false)
    private Double difficulty;

    @Column(nullable = false)
    private Integer participantCount;

}
