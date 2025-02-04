package com.example.masterplanbbe.domain.exam.entity;

import com.example.masterplanbbe.common.annotation.NonNull;
import com.example.masterplanbbe.common.domain.FullAuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subjects")
@Getter
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

    @Builder
    public Subject(Exam exam, String title, String description) {
        this.exam = exam;
        this.title = title;
        this.description = description;
    }

    public void update(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
