package com.example.masterplanbbe.member.service;


import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepository;
import com.example.masterplanbbe.member.service.request.MemberCreateRequest;
import com.example.masterplanbbe.member.service.request.MemberUpdateRequest;
import com.example.masterplanbbe.member.service.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse find(Long id) {
        return MemberResponse.from(memberRepository.findById(id).orElseThrow());
    }

    public MemberResponse create(MemberCreateRequest request) {
        Member member = memberRepository.save(
                Member.create(
                        request.getUserId(),request.getEmail(),request.getName(), request.getNickname(),
                        request.getPassword(),request.getPhoneNumber(),request.getBirthday(),
                        request.getProfileImageUrl(), request.getRole()

                )
        );
        return MemberResponse.from(member);
    }

    public MemberResponse update(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id).orElseThrow();
        member.update(
                request.getUserId(), request.getEmail(), request.getName(), request.getNickname(),
                request.getPassword(), request.getPhoneNumber(), request.getBirthday(),
                request.getProfileImageUrl(), request.getRole()
        );
        return MemberResponse.from(member);
    }

    public void delete(Long id) {
        Member member = memberRepository.findById(id).orElseThrow();
        memberRepository.delete(member);
    }
}
