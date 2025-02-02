package com.example.masterplanbbe.domain.exam.entity;

import com.example.masterplanbbe.common.annotation.NonNull;
import com.example.masterplanbbe.common.domain.FullAuditEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subjects")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subject extends FullAuditEntity {
    @NonNull
    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @NonNull
    @Column
    private String title;

    @NonNull
    @Column
    private String description;
}
