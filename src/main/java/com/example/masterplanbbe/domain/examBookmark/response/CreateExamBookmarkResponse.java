package com.example.masterplanbbe.domain.examBookmark.response;

import com.example.masterplanbbe.domain.examBookmark.entity.ExamBookmark;

public record CreateExamBookmarkResponse(
        Long examBookmarkId,
        Long examId,
        Long memberId
) {
    public CreateExamBookmarkResponse(ExamBookmark examBookmark) {
        this(
                examBookmark.getId(),
                examBookmark.getExam().getId(),
                examBookmark.getMember().getId()
        );
    }
}
