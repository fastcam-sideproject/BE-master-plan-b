package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}
