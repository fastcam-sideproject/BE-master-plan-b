package com.example.masterplanbbe.domain.member.repository;

import com.example.masterplanbbe.common.exception.GlobalException;
import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryAdapter implements MemberRepositoryPort{

    private final MemberRepository memberRepository;

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND) {});
    }

    @Override
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND) {});
    }
}
