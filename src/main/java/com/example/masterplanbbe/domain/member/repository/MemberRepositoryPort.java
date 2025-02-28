package com.example.masterplanbbe.domain.member.repository;

import com.example.masterplanbbe.domain.member.entity.Member;

public interface MemberRepositoryPort {
    Member findById(Long memberId);
    Member findByEmail(String email);
}
