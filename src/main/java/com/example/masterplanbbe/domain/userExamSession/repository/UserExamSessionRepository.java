package com.example.masterplanbbe.domain.userExamSession.repository;

import com.example.masterplanbbe.domain.userExamSession.entity.UserExamSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserExamSessionRepository extends JpaRepository<UserExamSession, Long> {
    Optional<UserExamSession> findByIdAndMemberId(Long id, Long memberId);
}
