package com.example.masterplanbbe.domain.exam.entity;

import com.example.masterplanbbe.common.annotation.NonNull;
import com.example.masterplanbbe.common.annotation.Nullable;
import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "exams")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exam extends FullAuditEntity {
    @NonNull
    @Column
    private String title;

    @NonNull
    @Column
    private Category category;

    @NonNull
    @Column
    private String authority ;

    @NonNull
    @Column
    private Double difficulty;

    @NonNull
    @Column
    private Integer participantCount;

    @NonNull
    @Column
    private CertificationType certificationType;

    @Nullable
    @OneToMany(mappedBy = "exam")
    private List<Subject> subjects;

    @Builder
    public Exam(String title, Category category, String authority, Double difficulty, Integer participantCount, CertificationType certificationType, List<Subject> subjects) {
        this.title = title;
        this.category = category;
        this.authority = authority;
        this.difficulty = difficulty;
        this.participantCount = participantCount;
        this.certificationType = certificationType;
        this.subjects = subjects;
    }

}