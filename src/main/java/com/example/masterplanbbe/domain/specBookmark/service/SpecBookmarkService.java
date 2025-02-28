package com.example.masterplanbbe.domain.specBookmark.service;

import com.example.masterplanbbe.domain.exam.repository.ExamRepositoryPort;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.spec.repository.SpecRepositoryPort;
import com.example.masterplanbbe.domain.specBookmark.entity.SpecBookmark;
import com.example.masterplanbbe.domain.specBookmark.repository.SpecBookmarkRepository;
import com.example.masterplanbbe.domain.specBookmark.response.CreateSpecBookmarkResponse;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepositoryPort;
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
