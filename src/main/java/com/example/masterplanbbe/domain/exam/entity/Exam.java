package com.example.masterplanbbe.domain.exam.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exams")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exam extends FullAuditEntity {
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private CertificationType certificationType;

    @Column(nullable = false)
    private Double difficulty;

    @Column(nullable = false)
    private Integer participantCount;

    @Column
    private String preparation;

    @Column
    private String eligibility;

    @Column
    private String examStructure;

    @Column
    private String passingCriteria;

}