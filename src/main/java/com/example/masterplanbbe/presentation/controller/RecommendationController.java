package com.example.masterplanbbe.presentation.controller;

import com.example.masterplanbbe.application.dto.RecommendationSpecDTO;
import com.example.masterplanbbe.application.service.SortRecommendationService;
import com.example.masterplanbbe.application.service.UpdateJobRoleService;
import com.example.masterplanbbe.presentation.request.RecommendationRequest;
import com.example.masterplanbbe.presentation.response.ApiResponse;
import com.example.masterplanbbe.presentation.response.RecommendationResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Recommendation controller api", description = "추천 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommendations")
public class RecommendationController {

    private final SortRecommendationService sortRecommendationService;
    private final UpdateJobRoleService updateJobRoleService;

    @PostMapping("/update-job-roles")
    public ApiResponse<?> updateJobRoles(
            @RequestBody RecommendationRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        updateJobRoleService.updateMemberJobRoles(userDetails.getUsername(), request);
        return ApiResponse.ok("관심 직무들이 등록됐습니다");
    }

    @GetMapping
    public List<RecommendationResponseDTO> getRecommendations(
            @AuthenticationPrincipal UserDetails userDetails) {
        return sortRecommendationService.findSpecRecommendations(userDetails.getUsername())
                .stream().map(RecommendationResponseDTO::from).toList();
    }
}
