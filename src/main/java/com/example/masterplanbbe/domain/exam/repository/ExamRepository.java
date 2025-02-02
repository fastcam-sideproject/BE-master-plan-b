package com.example.masterplanbbe.domain.exam.repository;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}
