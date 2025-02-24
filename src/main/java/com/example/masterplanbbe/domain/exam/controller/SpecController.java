package com.example.masterplanbbe.domain.exam.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Spec controller api", description = "스펙 API")
@RestController
public class SpecController {
    private final SpecService specService;

    @Operation(summary = "스펙 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<SpecItemCardDto>>> getAllSpec(
            @PageableDefault Pageable pageable,
            @RequestParam(name = "memberId") String memberId
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(specService.getAllSpec(pageable, memberId)));
    }

    @Operation(summary = "스펙 상세 조회")
    @GetMapping("/{specId}")
    public ResponseEntity<ApiResponse<ReadSpecResponse>> getSpec(
            @PathVariable("specId") Long specId
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(specService.getSpec(specId)));
    }

    @Operation(summary = "스펙 등록")
    @PostMapping("")
    public ResponseEntity<ApiResponse<CreateSpecResponse>> create(
            @RequestBody SpecCreateRequest request
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(specService.create(request)));
    }

    @Operation(summary = "스펙 수정")
    @PatchMapping("/{specId}")
    public ResponseEntity<ApiResponse<UpdateSpecResponse>> update(
            @PathVariable("specId") Long specId,
            @RequestBody SpecUpdateRequest request
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(specService.update(specId, request)));
    }

    @Operation(summary = "스펙 삭제")
    @DeleteMapping("/{specId}")
    public ResponseEntity<ApiResponse<DeleteSpecResponse>> delete(
            @PathVariable("specId") Long specId
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(specService.delete(specId)));
    }
}
