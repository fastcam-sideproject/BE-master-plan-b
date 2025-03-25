package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.entity.MemberSpec;
import com.example.masterplanbbe.domain.repository.MemberSpecRepository;
import com.example.masterplanbbe.domain.repository.MemberSpecRepositoryPort;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;
import com.example.masterplanbbe.infrastructure.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberSpecRepositoryAdapter implements MemberSpecRepositoryPort {

    private final MemberSpecRepository memberSpecRepository;

    @Override
    public MemberSpec save(MemberSpec spec) {
        return memberSpecRepository.save(spec);
    }

    @Override
    public MemberSpec findById(Long id) {
        return memberSpecRepository.findById(id)
                .orElseThrow(() -> new GlobalException.NotFoundException(ErrorCode.SPEC_NOT_FOUND));
    }

    @Override
    public void deleteById(Long id) {
        memberSpecRepository.deleteById(id);
    }

    @Override
    public List<MemberSpec> findAllByMember(Member member) {
        return memberSpecRepository.findAllByMember(member);
    }

}
