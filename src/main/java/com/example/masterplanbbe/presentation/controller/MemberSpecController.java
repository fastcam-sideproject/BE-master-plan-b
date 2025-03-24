package com.example.masterplanbbe.presentation.controller;

import com.example.masterplanbbe.application.service.MemberSpecService;
import com.example.masterplanbbe.presentation.request.MemberSpecRequest;
import com.example.masterplanbbe.presentation.response.ApiResponse;
import com.example.masterplanbbe.presentation.response.MemberSpecResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "MemberSpec controller api", description = "내 자격증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/my/spec")
public class MemberSpecController {

    private final MemberSpecService memberSpecService;

    @Operation(summary = "자격증 등록")
    @PostMapping("")
    public ResponseEntity<ApiResponse<MemberSpecResponse>> createMemberSpec(
            @RequestBody MemberSpecRequest memberSpecRequest,
            Authentication authentication
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(memberSpecService.createMemberSpec(authentication.getName(), memberSpecRequest)));
    }

    @Operation(summary = "자격증 조회")
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<MemberSpecResponse>>> getAllMemberSpec(
            Authentication authentication
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(memberSpecService.getMemberSpecList(authentication.getName())));
    }

    @Operation(summary = "자격증 삭제")
    @DeleteMapping("/{specId}")
    public ResponseEntity<ApiResponse<Void>> deleteMemberSpec(
            Authentication authentication,
            @PathVariable Long specId
    ) {
        memberSpecService.deleteMemberSpec(authentication.getName(), specId);

        return ResponseEntity.ok().body(ApiResponse.ok());
    }

    @Operation(summary = "자격증 수정")
    @PatchMapping("/{specId}")
    public ResponseEntity<ApiResponse<MemberSpecResponse>> modifiedMemberSpec(
            @RequestBody MemberSpecRequest memberSpecRequest,
            @PathVariable Long specId,
            Authentication authentication
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(memberSpecService.updateMemberSpec(authentication.getName(), specId, memberSpecRequest)));
    }

}
