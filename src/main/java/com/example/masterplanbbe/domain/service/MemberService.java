package com.example.masterplanbbe.domain.service;


import com.example.masterplanbbe.infrastructure.exception.ErrorCode;
import com.example.masterplanbbe.infrastructure.exception.GlobalException;
import com.example.masterplanbbe.presentation.request.*;
import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.repository.MemberRepository;
import com.example.masterplanbbe.domain.enums.MemberRoleEnum;
import com.example.masterplanbbe.infrastructure.exception.DuplicateUserException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private static final String VERIFICATION = "VERIFY_";

    @Value("${spring.mail.username}")
    private String username;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final RedisTemplate<String, Integer> verifyEmailTemplate;

    /**
     * 이메일 중복 확인 및 해당 이메일 인증번호 발송
     * @param dto 인증번호 수신 및 가입 예정 이메일 DTO
     */
    public void sendMailForVerification(MemberEmailSendDTO dto) {
        String email = dto.email();

        // 중복 이메일 검증
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new DuplicateUserException(ErrorCode.DUPLICATE_USER_EMAIL);
        }

        // 인증번호 발송
        SecureRandom secureRandom = new SecureRandom();
        int verificationNumber = 100000 + secureRandom.nextInt(900000);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            String title = "[마스터플랜비]회원가입 이메일 인증번호입니다";
            String content = verificationNumber + "<br />" + "인증번호를 입력해주세요";

            helper.setFrom(username);
            helper.setTo(email);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(mimeMessage);

            verifyEmailTemplate.opsForValue().set(
                    VERIFICATION + email, verificationNumber, 3, TimeUnit.MINUTES);
        } catch (MessagingException e) {
            log.error(e.getMessage());
            throw new GlobalException.InternalServerException(ErrorCode.INTERNAL_MAIL_EXCEPTION);
        }
    }

    /**
     * 인증번호 일치 확인
     * @param dto 가입 예정 이메일 및 해당 수신 인증번호
     */
    public void verifyEmail(MemberVerificationDTO dto) {
        String email = dto.email();
        Integer verificationNumber = dto.verification();

        // 인증번호 만료
        if (Boolean.FALSE.equals(verifyEmailTemplate.hasKey(VERIFICATION + email))) {
            throw new GlobalException.BadRequestException(ErrorCode.EXPIRED_VERIFICATION);
        }

        // 인증번호 불일치
        if (!Objects.equals(
                verifyEmailTemplate.opsForValue().get(VERIFICATION + email), verificationNumber)) {
            throw new GlobalException.BadRequestException(ErrorCode.INCORRECT_VERIFICATION);
        }

        // 소모된 인증번호 삭제
        verifyEmailTemplate.delete(VERIFICATION + email);
    }

    /**
     * 신규 사용자 회원가입
     * @param request 회원가입 DTO
     */
    public void createMember(MemberCreateRequestDTO request) {
        // 중복 이메일 검증
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

    public void updateMemberAge(String email, AgeUpdateRequest request) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                EntityNotFoundException::new);
        member.updateAge(request.birthDate());
    }
}
