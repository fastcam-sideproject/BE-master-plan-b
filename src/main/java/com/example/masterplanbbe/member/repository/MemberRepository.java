package com.example.masterplanbbe.member.repository;

import com.example.masterplanbbe.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
