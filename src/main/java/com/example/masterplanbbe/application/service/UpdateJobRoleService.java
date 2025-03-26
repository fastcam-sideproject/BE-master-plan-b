package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.repository.JobRoleRepository;
import com.example.masterplanbbe.domain.repository.MemberRepository;
import com.example.masterplanbbe.presentation.request.RecommendationRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateJobRoleService {

    private final MemberRepository memberRepository;
    private final JobRoleRepository jobRoleRepository;

    public void updateMemberJobRoles(String email, RecommendationRequest request) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                EntityNotFoundException::new);
        request.jobRoles().stream().map(
                e -> jobRoleRepository.findByJobRoleName(e).orElseThrow(
                        EntityNotFoundException::new)
        ).forEach(member::addJobRole);
    }
}
