package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.entity.MemberSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberSpecRepositoryPort {
    MemberSpec save(MemberSpec spec);

    MemberSpec findById(Long id);

    void deleteById(Long id);

    List<MemberSpec> findAllByMember(Member member);

}
