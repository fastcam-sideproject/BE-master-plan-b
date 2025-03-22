package com.example.masterplanbbe.presentation.controller;

import com.example.masterplanbbe.application.service.StatisticsService;
import com.example.masterplanbbe.presentation.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(name = "스펙 통계 controller api", description = "스펙 통계 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/specs/{specId}")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<Map<String, Map<String, List<Map<String, Object>>>>>> getStatistics(
            @PathVariable Long specId
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(statisticsService.getStatisticsForBoth()));
    }
}
