package com.example.masterplanbbe.domain.member.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.member.service.MemberService;
import com.example.masterplanbbe.domain.member.dto.MemberCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member controller api", description = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "멤버 생성")
    @PostMapping("/create")
    public ApiResponse<?> create(@RequestBody MemberCreateRequest request) {
        memberService.createMember(request);
        return ApiResponse.ok("회원가입이 완료됐습니다.");
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
