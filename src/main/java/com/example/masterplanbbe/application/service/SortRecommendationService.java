package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.domain.entity.JobRole;
import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.entity.MemberJobRole;
import com.example.masterplanbbe.domain.entity.Spec;
import com.example.masterplanbbe.domain.enums.AgeGroup;
import com.example.masterplanbbe.domain.repository.MemberRepository;
import com.example.masterplanbbe.domain.repository.SpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SortRecommendationService {

    private final SpecRepository specRepository;
    private final MemberRepository memberRepository;

    public List<Spec> findSpecRecommendations(String email) {
        Member member = memberRepository.findByEmailWithJobRoles(email).orElseThrow(
                // 예외 처리
        );

        // 연령대, 관심 직무들 추출
        AgeGroup age = AgeGroup.getAgeGroup(member.getBirthdate());
        List<JobRole> memberJobRoles = member.getMemberJobRoles()
                .stream()
                .map(MemberJobRole::getJobRole)
                .toList();

        // 관심 직무 이름 목록
        List<String> jobRoleNames = memberJobRoles.stream()
                .map(JobRole::getJobRoleName)
                .toList();

        // 추천 스펙 리스트 추출

        return null;
    }
}
