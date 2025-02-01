package com.example.masterplanbbe.member.repository;

import com.example.masterplanbbe.common.GlobalException;
import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.member.entity.Member;
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
}
