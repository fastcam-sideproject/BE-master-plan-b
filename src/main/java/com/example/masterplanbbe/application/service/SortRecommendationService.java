package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.application.dto.RecommendationSpecDTO;
import com.example.masterplanbbe.domain.entity.*;
import com.example.masterplanbbe.domain.enums.AgeGroup;
import com.example.masterplanbbe.domain.repository.MemberRepository;
import com.example.masterplanbbe.domain.repository.RecommendationRepository;
import com.example.masterplanbbe.domain.repository.RecommendationRepositoryCustom;
import com.example.masterplanbbe.infrastructure.repository.RecommendationRepositoryAdapter;
import com.example.masterplanbbe.presentation.response.RecommendationResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final RecommendationRepositoryAdapter recommendationSpecRepository;

    private final RecommendationRepositoryCustom recommendationRepositoryCustom;

    public List<RecommendationSpecDTO> findSpecRecommendations(String email) {
        log.info("이메일: {}", email);

        Member member = memberRepository.findMemberWithJobRoles(email)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

        AgeGroup ageGroup = AgeGroup.getAgeGroup(member.getBirthdate());
        List<JobRole> jobRoles = member.getMemberJobRoles().stream()
                .map(MemberJobRole::getJobRole)
                .toList();
        log.info("관심 직무들: {}", jobRoles.stream().map(JobRole::getJobRoleName).toList());
        List<Category> categories = jobRoles.stream()
                .map(JobRole::getCategory)
                .distinct()
                .toList();
        log.info("직무들의 카테고리: {}", categories.stream().map(Category::getCategoryName).toList());

        List<RecommendationSpecDTO> recommendationSpecs = new ArrayList<>();

        // 관심 직무가 있는지 없는지?
        if (jobRoles.isEmpty()) {
            // 관심 직무가 없다면 해당 연령대를 기반으로 상위 6개를 뽑아오면 됨
            recommendationSpecs.addAll(
                    recommendationRepositoryCustom.findByAgeGroupWithoutJobRoles(ageGroup));
        } else {
            // 일단 관심 직무를 기반으로 추천
            List<RecommendationSpecDTO> byAgeGroupWithJobRoles =
                    recommendationRepositoryCustom.findByAgeGroupWithJobRoles(ageGroup, jobRoles);
            recommendationSpecs.addAll(byAgeGroupWithJobRoles.stream().distinct().toList());

            // 여전히 사이즈가 부족하다면 형제 직무를 기반으로 추천
            if (recommendationSpecs.size() < RECOMMENDATION_COUNT) {
                List<RecommendationSpecDTO> byAgeGroupAndNotJobRoles =
                        recommendationRepositoryCustom.findByAgeGroupAndNotJobRoles(ageGroup, jobRoles, categories);
                recommendationSpecs.addAll(byAgeGroupAndNotJobRoles.stream().distinct().toList());
            }

            // 여전히 사이즈가 부족하다면 해당 카테고리들을 제외한 내용 기반으로 추천
            if (recommendationSpecs.size() < RECOMMENDATION_COUNT) {
                List<RecommendationSpecDTO> byAgeGroupAndNotCategories =
                        recommendationRepositoryCustom.findByAgeGroupAndNotCategories(ageGroup, categories);
                recommendationSpecs.addAll(byAgeGroupAndNotCategories.stream().distinct().toList());
            }
        }

        return recommendationSpecs.subList(0, 6);
    }
}
