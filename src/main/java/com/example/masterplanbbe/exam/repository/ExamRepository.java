package com.example.masterplanbbe.exam.repository;

import com.example.masterplanbbe.exam.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long>, ExamRepositoryCustom {
}
