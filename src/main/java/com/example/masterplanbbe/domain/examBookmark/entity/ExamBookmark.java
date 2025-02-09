package com.example.masterplanbbe.domain.examBookmark.entity;

import com.example.masterplanbbe.common.annotation.NonNull;
import com.example.masterplanbbe.common.domain.IdAndCreatedEntity;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exam_bookmarks")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExamBookmark extends IdAndCreatedEntity {
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    private Exam exam;
}
