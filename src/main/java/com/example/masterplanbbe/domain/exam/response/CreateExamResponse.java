package com.example.masterplanbbe.domain.exam.response;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.entity.Subject;
import com.example.masterplanbbe.domain.exam.enums.Category;

import java.util.List;

public record CreateExamResponse(
        Long examId,
        String title,
        Category category,
        String authority,
        List<Subject> subjects
) {
    public CreateExamResponse(Exam exam){
        this(exam.getId(), exam.getTitle(), exam.getCategory(), exam.getAuthority(), exam.getSubjects());
    }
}
