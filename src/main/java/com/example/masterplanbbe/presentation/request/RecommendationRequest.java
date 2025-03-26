package com.example.masterplanbbe.presentation.request;

import java.util.List;

public record RecommendationRequest(
        List<String> jobRoles
) {
}
