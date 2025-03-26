package com.example.masterplanbbe.presentation.controller;

import com.example.masterplanbbe.application.service.UpdateJobRoleService;
import com.example.masterplanbbe.presentation.request.*;
import com.example.masterplanbbe.presentation.response.ApiResponse;
import com.example.masterplanbbe.domain.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member controller api", description = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final UpdateJobRoleService updateJobRoleService;

    @Operation(summary = "이메일 중복 확인 및 인증번호 발송")
    @PostMapping("/send-verification-code")
    public ApiResponse<?> sendMailForVerification(@RequestBody MemberEmailSendDTO dto) {
        memberService.sendMailForVerification(dto);
        return ApiResponse.ok("인증번호가 발송됐습니다.");
    }

    @Operation(summary = "인증번호 일치 확인")
    @PostMapping("/verification")
    public ApiResponse<?> verifyEmail(@RequestBody MemberVerificationDTO dto) {
        memberService.verifyEmail(dto);
        return ApiResponse.ok("인증번호가 확인됐습니다.");
    }

    @Operation(summary = "회원가입")
    @PostMapping("/create")
    public ApiResponse<?> create(@Valid @RequestBody MemberCreateRequestDTO request) {
        memberService.createMember(request);
        return ApiResponse.ok("회원가입이 완료됐습니다.");
    }

    @Operation(summary = "연령대 업데이트")
    @PostMapping("/update-age")
    public ApiResponse<?> updateAge(
            @RequestBody AgeUpdateRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        memberService.updateMemberAge(userDetails.getUsername(), request);
        return ApiResponse.ok("생년월일이 등록됐습니다");
    }

    @Operation(summary = "관심 직무 업데이트")
    @PostMapping("/update-job-roles")
    public ApiResponse<?> updateJobRoles(
            @RequestBody RecommendationRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        updateJobRoleService.updateMemberJobRoles(userDetails.getUsername(), request);
        return ApiResponse.ok("관심 직무들이 등록됐습니다");
    }

    @GetMapping("/test")
    public String test() {
        return "스프링 시큐리티 테스트";
    }

    @GetMapping("/security")
    public String security() {
        return "인증되어야만 볼 수 있음 ㅋ";
    }
}
