package com.example.masterplanbbe.member.repository;

import com.example.masterplanbbe.member.entity.Member;

public interface MemberRepositoryPort {
    Member findById(Long id);
}
