package com.example.masterplanbbe.domain.exam.entity;

import com.example.masterplanbbe.common.annotation.NonNull;
import com.example.masterplanbbe.common.annotation.Nullable;
import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.domain.exam.dto.SubjectDto;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import com.example.masterplanbbe.domain.exam.request.ExamUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @Nullable
    @OneToMany(mappedBy = "exam")
    private List<Subject> subjects = List.of();

    @Builder
    public Exam(String title,
                Category category,
                String authority,
                Double difficulty,
                Integer participantCount,
                CertificationType certificationType,
                List<Subject> subjects) {
        this.title = title;
        this.category = category;
        this.authority = authority;
        this.difficulty = difficulty;
        this.participantCount = participantCount;
        this.certificationType = certificationType;
        this.subjects = subjects != null ? subjects : List.of();
    }

    public void update(ExamUpdateRequest request) {
        this.title = request.title();
        this.category = request.category();
        this.authority = request.authority();
        this.difficulty = request.difficulty();
        this.participantCount = request.participantCount();
        this.certificationType = request.certificationType();
        this.subjects = getUpdatedSubjects(request.subjects());
    }

    private List<Subject> getUpdatedSubjects(List<SubjectDto> subjectDtos) {
        Map<Long, Subject> subjectMap = this.subjects != null ?
                this.subjects.stream().collect(Collectors.toMap(Subject::getId, Function.identity())) :
                Map.of();

        return subjectDtos.stream()
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