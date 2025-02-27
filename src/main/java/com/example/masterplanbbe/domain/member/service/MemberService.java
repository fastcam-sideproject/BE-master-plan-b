package com.example.masterplanbbe.domain.member.service;


import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.member.repository.MemberRepository;
import com.example.masterplanbbe.domain.member.entity.MemberRoleEnum;
import com.example.masterplanbbe.domain.member.exception.DuplicateUserException;
import com.example.masterplanbbe.domain.member.dto.MemberCreateRequest;
import com.example.masterplanbbe.domain.member.dto.MemberResponse;
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
    public void createMember(MemberCreateRequest request) {
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateUserException(ErrorCode.DUPLICATE_USER_EMAIL);
        }

//        MemberRoleEnum role = null;
        MemberRoleEnum role = MemberRoleEnum.USER;

//        if (request.getRole().equals("USER")) {
//            role = MemberRoleEnum.USER;
//        } else if (request.getRole().equals("ADMIN")) {
//            role = MemberRoleEnum.ADMIN;
//        }

        String password = passwordEncoder.encode(request.getPassword());
        Member member = new Member(request, password, role);
        memberRepository.save(member);
    }
}
