package com.example.masterplanbbe.exam.entity;

import com.example.masterplanbbe.common.annotation.NonNull;
import com.example.masterplanbbe.common.domain.BaseEntity;
import com.example.masterplanbbe.exam.enums.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exam")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exam extends BaseEntity {
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


}
