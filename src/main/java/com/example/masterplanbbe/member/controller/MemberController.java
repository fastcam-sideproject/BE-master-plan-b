package com.example.masterplanbbe.member.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.member.service.MemberService;
import com.example.masterplanbbe.member.dto.MemberCreateRequest;
import com.example.masterplanbbe.member.dto.MemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member controller api", description = "멤버 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "멤버 생성")
    @PostMapping("/create")
    public ApiResponse<MemberResponse> create(@RequestBody MemberCreateRequest request) {
        return ApiResponse.ok(memberService.createMember(request));
    }

    @GetMapping("/test")
    public String test() {
        return "스프링 시큐리티 테스트";
    }

    @GetMapping("/fail")
    public String fail() {
        return "넌 당연히 테스트 통과 x";
    }
}
