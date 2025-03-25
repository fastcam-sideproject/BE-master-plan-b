//package com.example.masterplanbbe.application.service;
//
//import com.example.masterplanbbe.domain.entity.*;
//import com.example.masterplanbbe.domain.enums.AgeGroup;
//import com.example.masterplanbbe.domain.repository.MemberRepository;
//import com.example.masterplanbbe.domain.repository.RecommendationRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Slf4j
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class SortRecommendationService {
//
//    private static final int RECOMMENDATION_COUNT = 6;
//
//    private final MemberRepository memberRepository;
//    private final RecommendationRepository recommendationRepository;
//
//    public List<Spec> findSpecRecommendations(String email) {
//        log.info("이메일: {}", email);
//        Member member = memberRepository.findByEmailWithJobRoles(email)
//                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
//
//        // 연령대 및 관심 직무 추출
//        AgeGroup ageGroup = AgeGroup.getAgeGroup(member.getBirthdate());
//        List<String> jobRoleNames = member.getMemberJobRoles().stream()
//                .map(MemberJobRole::getJobRole)
//                .map(JobRole::getJobRoleName)
//                .toList();
//
//        // temp
////        ageGroup = AgeGroup.EARLY_20S;
////        jobRoleNames = Arrays.asList("웹 개발자", "UI/UX 디자이너", "초·중·고등학교 교사");
//
//        List<Spec> recommendations = new ArrayList<>();
//
//        // 관심 직무 기반 추천 (최대 6개)
//        // 관심 직무는
//        if (!jobRoleNames.isEmpty()) {
//            List<Recommendation> jobRoleRecommendations = recommendationRepository.findTopByJobRolesAndAge(
//                    jobRoleNames, ageGroup, PageRequest.of(0, RECOMMENDATION_COUNT));
//            recommendations.addAll(jobRoleRecommendations.stream().map(Recommendation::getSpec).toList());
//            log.info("관심 직무 추천 리스트: {}", jobRoleRecommendations.stream().map(e -> e.getSpec().getName()).toList());
//        }
//
//        // 부족하면 형제 직무(같은 category) 추천 추가
//        if (recommendations.size() < RECOMMENDATION_COUNT) {
//            List<Long> categoryIds = member.getMemberJobRoles().stream()
//                    .map(MemberJobRole::getJobRole)
//                    .map(JobRole::getCategory)
//                    .map(Category::getId)
//                    .distinct()
//                    .toList();
//
////            categoryIds = Arrays.asList(6L, 5L, 3L);
//
//            log.info("관심 직무의 카테고리 분야 ID: {}", categoryIds);
//
//            List<Recommendation> categoryRecommendations = recommendationRepository.findTopByCategoryAndAge(
//                    categoryIds, ageGroup, PageRequest.of(0, RECOMMENDATION_COUNT - recommendations.size()));
//            log.info("형제 직무 추천 리스트: {}", categoryRecommendations.stream().map(e -> e.getSpec().getName()).toList());
//
//            recommendations.addAll(categoryRecommendations.stream().map(Recommendation::getSpec).toList());
//        }
//
//        // 그래도 부족하면 전체 연령대 기반 추천 추가
//        if (recommendations.size() < RECOMMENDATION_COUNT) {
//            List<Recommendation> generalRecommendations = recommendationRepository.findTopByAge(
//                    ageGroup, PageRequest.of(0, RECOMMENDATION_COUNT - recommendations.size()));
//
//            recommendations.addAll(generalRecommendations.stream().map(Recommendation::getSpec).toList());
//            log.info("일반 직무 추천 리스트: {}", generalRecommendations.stream().map(e -> e.getSpec().getName()).toList());
//        }
//
//        return recommendations;
//    }
//}
