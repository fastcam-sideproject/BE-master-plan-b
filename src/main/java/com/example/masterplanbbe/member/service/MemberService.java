package com.example.masterplanbbe.member.service;


import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.entity.MemberRoleEnum;
import com.example.masterplanbbe.member.repository.MemberRepository;
import com.example.masterplanbbe.member.dto.MemberCreateRequest;
import com.example.masterplanbbe.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public MemberResponse createMember(MemberCreateRequest request) {
        if (memberRepository.findByUserId(request.getUserId()).isPresent() || memberRepository.findByEmail(request.getEmail()).isPresent()) {
            // 커스텀 예외: 이미 가입되어 있는 사용자
            throw new IllegalArgumentException("User already exists");
        }

        MemberRoleEnum role = null;

        if (request.getRole().equals("USER")) {
            role = MemberRoleEnum.USER;
        } else if (request.getRole().equals("ADMIN")) {
            role = MemberRoleEnum.ADMIN;
        }

        String password = passwordEncoder.encode(request.getPassword());
        Member member = Member.create(request, password, role);
        memberRepository.save(member);

        return new MemberResponse(member);
    }
}
