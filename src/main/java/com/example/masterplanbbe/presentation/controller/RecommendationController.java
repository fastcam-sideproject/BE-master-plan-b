package com.example.masterplanbbe.presentation.controller;

import com.example.masterplanbbe.presentation.request.RecommendationRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Recommendation controller api", description = "추천 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommendations")
public class RecommendationController {

    @GetMapping
    public void getRecommendations(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody RecommendationRequest recommendationRequest) {
        
    }
}
