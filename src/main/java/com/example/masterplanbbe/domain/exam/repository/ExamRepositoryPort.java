package com.example.masterplanbbe.domain.exam.repository;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepositoryPort extends ExamRepositoryCustom {
    Exam getById(Long examId);
    Exam save(Exam exam);
    void saveAll(Iterable<Exam> exams);
    void deleteAll();
}
