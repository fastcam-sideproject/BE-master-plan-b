package com.example.masterplanbbe.domain.userExamSession.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.userExamSession.dto.request.UserExamSessionRequest;
import com.example.masterplanbbe.domain.userExamSession.service.UserExamSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/user-exam-sessions")
@Tag(name = "User Exam Session controller api", description = "회원 시험 일정 API")
public class UserExamSessionController {

    private final UserExamSessionService userExamSessionService;

    @Operation(summary = "회원 시험 일정 등록")
    @PostMapping(path = "")
    public ResponseEntity<ApiResponse<?>> create(
            @RequestBody UserExamSessionRequest request,
            // TODO : security 적용 예정
            Long memberId
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(userExamSessionService.create(request, memberId)));
    }

    @Operation(summary = "회원 시험 일정 수정")
    @PatchMapping(path = "/{exam-sessions-id}")
    public ResponseEntity<ApiResponse<?>> update(
            @RequestBody UserExamSessionRequest request,
            @PathVariable(name = "exam-sessions-id") Long updateId
            // TODO : security 적용 예정
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(userExamSessionService.update(request, updateId)));
    }

    @Operation(summary = "회원 시험 일정 삭제")
    @DeleteMapping(path = "/{exam-sessions-id}")
    public ResponseEntity<ApiResponse<?>> delete(
            @PathVariable(name = "exam-sessions-id") Long deleteId,
            // TODO : security 적용 예정
            Long memberId
    ) {
        userExamSessionService.delete(deleteId, memberId);

        return ResponseEntity.ok()
                .body(ApiResponse.ok("나의 시험 일정 삭제 성공"));
    }

    @Operation(summary = "회원 시험 일정 상세 조회")
    @GetMapping(path = "/{exam-sessions-id}")
    public ResponseEntity<ApiResponse<?>> findOne(
            @PathVariable(name = "exam-sessions-id") Long id,
            // TODO : security 적용 예정
            Long memberId
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(userExamSessionService.findOne(id, memberId)));
    }

    @Operation(summary = "회원 시험 일정 조회")
    @GetMapping(path = "")
    public ResponseEntity<ApiResponse<?>> findAll(
            @RequestParam Integer year,
            @RequestParam Integer month,
            @PageableDefault Pageable pageable,
            // TODO : security 적용 예정
            Long memberId
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(userExamSessionService.findAll(year, month, memberId, pageable)));
    }

}
