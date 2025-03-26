package com.example.masterplanbbe.presentation.controller;

import com.example.masterplanbbe.application.dto.RecommendationSpecDTO;
import com.example.masterplanbbe.application.service.SortRecommendationService;
import com.example.masterplanbbe.presentation.response.RecommendationResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Recommendation controller api", description = "추천 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommendations")
public class RecommendationController {

    private final SortRecommendationService sortRecommendationService;

    @GetMapping
    public List<RecommendationResponseDTO> getRecommendations(
            @AuthenticationPrincipal UserDetails userDetails) {
        return sortRecommendationService.findSpecRecommendations(userDetails.getUsername())
                .stream().map(RecommendationResponseDTO::from).toList();
    }
}
