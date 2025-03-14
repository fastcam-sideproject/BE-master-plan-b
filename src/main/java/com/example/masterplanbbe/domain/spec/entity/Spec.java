package com.example.masterplanbbe.domain.spec.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import com.example.masterplanbbe.domain.jobRole.entity.JobRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    private Category category; // 삭제 예정 필드(연관된 서비스 코드들 삭제 요망)

    @JoinColumn
    @ManyToOne
    private JobRole jobRole;

    @Enumerated(EnumType.STRING)
    private CertificationType certificationType;

    @Column(nullable = false)
    private Integer participantCount;

    @OneToMany(mappedBy = "spec", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamDetail> examDetails;

    @OneToOne(fetch = FetchType.LAZY)
    private Exam latestExam;


    public Spec(String name,
                String issuingOrganization,
                Category category,
                CertificationType certificationType,
                Integer participantCount,
                List<ExamDetail> examDetails) {
        this.name = name;
        this.issuingOrganization = issuingOrganization;
        this.category = category;
        this.certificationType = certificationType;
        this.participantCount = participantCount;
        this.examDetails = examDetails != null ? examDetails : new ArrayList<>();
    }

    public void update(String name, String issuingOrganization, Category category, CertificationType certificationType, Integer participantCount) {
        this.name = name;
        this.issuingOrganization = issuingOrganization;
        this.category = category;
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
