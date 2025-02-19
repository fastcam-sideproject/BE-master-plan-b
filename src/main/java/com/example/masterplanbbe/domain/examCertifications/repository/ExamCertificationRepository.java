package com.example.masterplanbbe.domain.examCertifications.repository;

import com.example.masterplanbbe.domain.examCertifications.entity.ExamCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamCertificationRepository extends JpaRepository<ExamCertification, Long> {
}
