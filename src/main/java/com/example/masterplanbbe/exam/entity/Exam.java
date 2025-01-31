package com.example.masterplanbbe.exam.entity;

import com.example.masterplanbbe.common.annotation.NonNull;
import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.exam.enums.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

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
    private String authority;

    @NonNull
    @Column
    private Double difficulty;

    @NonNull
    @Column
    private Integer participantCount;

    @Builder
    public Exam(String title, Category category, String authority, Double difficulty, Integer participantCount) {
        this.title = title;
        this.category = category;
        this.authority = authority;
        this.difficulty = difficulty;
        this.participantCount = participantCount;
    }

}