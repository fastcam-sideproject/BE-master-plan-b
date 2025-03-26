package com.example.masterplanbbe.domain.entity;

import com.example.masterplanbbe.domain.common.FullAuditEntity;
import com.example.masterplanbbe.domain.enums.SpecCategory;
import com.example.masterplanbbe.domain.enums.CertificationType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "specs")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Spec extends FullAuditEntity {
    @Column(nullable = false)
    private String name;

    @Column
    private String issuingOrganization;

    @Enumerated(EnumType.STRING)
    private SpecCategory specCategory; // 삭제 예정 필드(연관된 서비스 코드들 삭제 요망)

    @OneToMany(mappedBy = "spec", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JobRoleSpec> jobRoleSpecs = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private CertificationType certificationType;

    @Column(name = "participant_count")
    private Integer participantCount;

    @OneToMany(mappedBy = "spec", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamDetail> examDetails;

    @JoinColumn(name = "latest_exam")
    @OneToOne(fetch = FetchType.LAZY)
    private Exam latestExam;

    public Spec(String name,
                String issuingOrganization,
                SpecCategory specCategory,
                CertificationType certificationType,
                Integer participantCount,
                List<ExamDetail> examDetails) {
        this.name = name;
        this.issuingOrganization = issuingOrganization;
        this.specCategory = specCategory;
        this.certificationType = certificationType;
        this.participantCount = participantCount;
        this.examDetails = examDetails != null ? examDetails : new ArrayList<>();
    }

    public void update(String name, String issuingOrganization, SpecCategory specCategory, CertificationType certificationType, Integer participantCount) {
        this.name = name;
        this.issuingOrganization = issuingOrganization;
        this.specCategory = specCategory;
        this.certificationType = certificationType;
        this.participantCount = participantCount;
    }

    public void addExamDetail(ExamDetail examDetail) {
        this.examDetails.add(examDetail);
    }

    public void specifyLatestExam(Exam exam) {
        this.latestExam = exam;
    }
}
