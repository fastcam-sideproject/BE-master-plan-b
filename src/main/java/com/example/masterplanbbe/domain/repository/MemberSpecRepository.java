package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.entity.MemberSpec;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MemberSpecRepository extends JpaRepository<MemberSpec, Long> {
    List<MemberSpec> findAllByMember(Member member);
}
