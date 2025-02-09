package com.example.masterplanbbe.domain.examBookmark.repository;

import com.example.masterplanbbe.domain.examBookmark.entity.ExamBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamBookmarkRepository extends JpaRepository<ExamBookmark, Long> {
}
