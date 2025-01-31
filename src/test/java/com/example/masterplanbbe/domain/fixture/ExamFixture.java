package com.example.masterplanbbe.domain.fixture;

import com.example.masterplanbbe.exam.entity.Exam;

import static com.example.masterplanbbe.exam.enums.Category.*;

public class ExamFixture {
    public static Exam createExam(String title) {
        return Exam.builder()
                .title(title)
                .category(IT_ICT)
                .authority("테스트")
                .participantCount(100)
                .difficulty(3.0)
                .build();
    }
}
