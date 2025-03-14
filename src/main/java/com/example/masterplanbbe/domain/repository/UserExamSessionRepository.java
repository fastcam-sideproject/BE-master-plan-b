package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.UserExamSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserExamSessionRepository extends JpaRepository<UserExamSession, Long> {
    Optional<UserExamSession> findByIdAndMemberEmail(Long id, String memberId);
}
