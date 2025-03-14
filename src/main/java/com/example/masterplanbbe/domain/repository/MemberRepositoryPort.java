package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.Member;

public interface MemberRepositoryPort {
    Member findById(Long memberId);
    Member findByEmail(String email);
}
