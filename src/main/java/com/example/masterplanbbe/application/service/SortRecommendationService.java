package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.domain.entity.*;
import com.example.masterplanbbe.domain.enums.AgeGroup;
import com.example.masterplanbbe.domain.repository.MemberRepository;
import com.example.masterplanbbe.domain.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SortRecommendationService {

    private static final int RECOMMENDATION_COUNT = 6;

    private final MemberRepository memberRepository;
    private final RecommendationRepository recommendationRepository;

    public List<Spec> findSpecRecommendations(String email) {
        log.info("이메일: {}", email);

        Member member = memberRepository.findMemberWithJobRoles(email)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

        AgeGroup ageGroup = AgeGroup.getAgeGroup(member.getBirthdate());
        List<JobRole> jobRoles = member.getMemberJobRoles().stream()
                .map(MemberJobRole::getJobRole)
                .toList();
        List<Category> categories = jobRoles.stream()
                .map(JobRole::getCategory)
                .toList();

        List<Spec> recommendations = new ArrayList<>();

        if (!jobRoles.isEmpty()) {
            addRecommendations(recommendations,
                    getJobRoleRecommendations(jobRoles, ageGroup),
                    RECOMMENDATION_COUNT);
        }

        if (recommendations.size() < RECOMMENDATION_COUNT) {
            addRecommendations(recommendations,
                    getCategoryRecommendations(jobRoles, categories, ageGroup),
                    RECOMMENDATION_COUNT - recommendations.size());
        }

        if (recommendations.size() < RECOMMENDATION_COUNT) {
            addRecommendations(recommendations,
                    getGeneralRecommendations(categories, ageGroup),
                    RECOMMENDATION_COUNT - recommendations.size());
        }

        return recommendations;
    }

    private List<Recommendation> getJobRoleRecommendations(List<JobRole> jobRoles, AgeGroup ageGroup) {
        return recommendationRepository.findTopByJobRolesAndAge(jobRoles, ageGroup,
                PageRequest.of(0, RECOMMENDATION_COUNT));
    }

    private List<Recommendation> getCategoryRecommendations(List<JobRole> jobRoles, List<Category> categories, AgeGroup ageGroup) {
        return recommendationRepository.findTopByCategoryAndAge(jobRoles, categories, ageGroup,
                PageRequest.of(0, RECOMMENDATION_COUNT));
    }

    private List<Recommendation> getGeneralRecommendations(List<Category> categories, AgeGroup ageGroup) {
        return recommendationRepository.findTopByAge(categories, ageGroup,
                PageRequest.of(0, RECOMMENDATION_COUNT));
    }

    private void addRecommendations(List<Spec> recommendations, List<Recommendation> newRecommendations, int limit) {
        int remaining = Math.min(limit, newRecommendations.size());
        recommendations.addAll(newRecommendations.subList(0, remaining).stream()
                .map(Recommendation::getSpec)
                .toList());
    }
}
