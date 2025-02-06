package com.example.masterplanbbe.domain.exam.entity;

import com.example.masterplanbbe.common.annotation.NonNull;
import com.example.masterplanbbe.common.annotation.Nullable;
import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.domain.exam.dto.SubjectDto;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import com.example.masterplanbbe.domain.exam.request.ExamUpdateRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @NonNull
    @Column
    private CertificationType certificationType;

    @NonNull
    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects = List.of();

    @NonNull
    @OneToOne(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private ExamDetail examDetail;

    @Builder
    public Exam(@NonNull String title,
                @NonNull Category category,
                @NonNull String authority,
                @NonNull Double difficulty,
                @NonNull Integer participantCount,
                @NonNull CertificationType certificationType,
                List<Subject> subjects,
                @NonNull ExamDetail examDetail) {
        this.title = title;
        this.category = category;
        this.authority = authority;
        this.difficulty = difficulty;
        this.participantCount = participantCount;
        this.certificationType = certificationType;
        this.subjects = subjects != null ? subjects : List.of();
        this.examDetail = examDetail;
    }

    public void update(ExamUpdateRequest request) {
        this.title = request.title();
        this.category = request.category();
        this.authority = request.authority();
        this.difficulty = request.difficulty();
        this.participantCount = request.participantCount();
        this.certificationType = request.certificationType();
        this.subjects = getUpdatedSubjects(request.subjects());
        examDetail.update(request);
    }

    private List<Subject> getUpdatedSubjects(List<SubjectDto> subjectDtoList) {
        Map<Long, Subject> subjectMap = this.subjects.stream().collect(Collectors.toMap(Subject::getId, Function.identity()));
        if (subjectDtoList == null) {
            return List.of();
        }

        return subjectDtoList.stream()
                .map(subjectDto -> {
                    Subject subject = subjectMap.get(subjectDto.id());
                    if (subject == null) {
                        return new Subject(this, subjectDto.title(), subjectDto.description());
                    }
                    subject.update(subjectDto.title(), subjectDto.description());
                    return subject;
                })
                .toList();
    }
}