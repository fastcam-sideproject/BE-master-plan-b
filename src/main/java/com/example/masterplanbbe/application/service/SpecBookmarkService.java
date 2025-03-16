package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.domain.entity.Spec;
import com.example.masterplanbbe.domain.repository.SpecRepositoryPort;
import com.example.masterplanbbe.domain.entity.SpecBookmark;
import com.example.masterplanbbe.domain.repository.SpecBookmarkRepository;
import com.example.masterplanbbe.presentation.response.CreateSpecBookmarkResponse;
import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.repository.MemberRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecBookmarkService {
    private final MemberRepositoryPort memberRepositoryPort;
    private final SpecRepositoryPort specRepositoryPort;
    private final SpecBookmarkRepository specBookmarkRepository;

    public CreateSpecBookmarkResponse createExamBookmark(Long memberId, Long specId) {
        Member member = memberRepositoryPort.findById(memberId);
        Spec spec = specRepositoryPort.getById(specId);
        return new CreateSpecBookmarkResponse(specBookmarkRepository.save(new SpecBookmark(member, spec)));
    }

    public void deleteExamBookmark(Long examBookmarkId) {
        specBookmarkRepository.deleteById(examBookmarkId);
    }
}
