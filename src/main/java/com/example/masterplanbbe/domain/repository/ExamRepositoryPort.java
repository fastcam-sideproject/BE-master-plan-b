package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.Exam;

public interface ExamRepositoryPort extends ExamRepositoryCustom {
    Exam getById(Long examId);
    Exam save(Exam exam);
    void saveAll(Iterable<Exam> exams);
    void deleteById(Long examId);
    void deleteAll();
}
