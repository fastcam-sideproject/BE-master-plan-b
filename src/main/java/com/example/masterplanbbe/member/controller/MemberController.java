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
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "멤버 생성")
    @PostMapping("/api/v1/member/create")
    public ApiResponse<MemberResponse> create(@RequestBody MemberCreateRequest request) {
        return ApiResponse.ok(memberService.createMember(request));
    }

}
