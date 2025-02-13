package com.example.masterplanbbe.domain.userExamSession.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.userExamSession.dto.request.UserExamSessionCreateRequest;
import com.example.masterplanbbe.domain.userExamSession.service.UserExamSessionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/user-exam-sessions")
@Tag(name = "User Exam Session controller api", description = "회원 시험 일정 API")
public class UserExamSessionController {

    private final UserExamSessionService userExamSessionService;

    @PostMapping(path = "")
    public ResponseEntity<ApiResponse<?>> create(
            @RequestBody UserExamSessionCreateRequest request,
            // TODO : security 적용 예정
            Long memberId
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(userExamSessionService.create(request, memberId)));
    }

}
