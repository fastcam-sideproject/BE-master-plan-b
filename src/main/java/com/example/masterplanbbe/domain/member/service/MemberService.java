package com.example.masterplanbbe.domain.member.service;


import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.domain.member.dto.MemberEmailVerificationDTO;
import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.member.repository.MemberRepository;
import com.example.masterplanbbe.domain.member.entity.MemberRoleEnum;
import com.example.masterplanbbe.domain.member.exception.DuplicateUserException;
import com.example.masterplanbbe.domain.member.dto.MemberCreateRequestDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    // 이메일 중복 확인 및 해당 이메일 인증번호 발송
    // MessageException 전역 예외 핸들러 등록하기
    public void verifyAndSendMail(MemberEmailVerificationDTO dto) {
        String email = dto.email();

        // 중복 이메일 검증
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new DuplicateUserException(ErrorCode.DUPLICATE_USER_EMAIL);
        }

        // 인증번호 발송
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            String title = "테스트 제목";
            String content = "테스트 내용";

            helper.setFrom(username);
            helper.setTo(email);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 신규 사용자 회원가입
     * @param request 회원가입 DTO
     */
    public void createMember(MemberCreateRequestDTO request) {
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
