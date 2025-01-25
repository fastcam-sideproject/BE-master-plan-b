package com.example.masterplanbbe.member.service;

import com.example.masterplanbbe.common.GlobalException;
import com.example.masterplanbbe.member.service.request.MemberCreateRequest;
import com.example.masterplanbbe.member.service.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Transactional
    public MemberResponse read(Long articleId) {
        return MemberResponse.from(memberRepository.findById(id).orElseThrow(() -> new GlobalException.NotFoundException("Member not found")));
    }

    public MemberResponse create(MemberCreateRequest request) {

    }

    public MemberResponse update(Long articleId, MemberUpdateRequest request) {
    }

    public void delete(Long articleId) {

    }
}
