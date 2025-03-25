package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.domain.entity.Spec;
import com.example.masterplanbbe.domain.enums.ExamType;
import com.example.masterplanbbe.domain.repository.SpecReviewRepository;
import com.example.masterplanbbe.domain.repository.SpecReviewRepositoryPort;
import com.example.masterplanbbe.domain.entity.SpecReview;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;
import com.example.masterplanbbe.infrastructure.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SpecReviewRepositoryAdapter implements SpecReviewRepositoryPort {

    private final SpecReviewRepository specReviewRepository;

    @Override
    public SpecReview findById(Long id) {
        return specReviewRepository.findById(id)
                .orElseThrow(() -> new GlobalException.NotFoundException(ErrorCode.NOT_FOUND_REVIEW));
    }

    @Override
    public void deleteById(Long id) {
        specReviewRepository.deleteById(id);
    }

    @Override
    public SpecReview save(SpecReview specReview) {
        return specReviewRepository.save(specReview);
    }

    @Override
    public SpecReview findByIdAndSpecId(Long id, Long specId) {
        return specReviewRepository.findByIdAndSpecId(id, specId);
    }

    @Override
    public boolean existsBySpecIdAndMemberEmail(Long memberSpecId, String email) {
        return specReviewRepository.existsByMemberSpecIdAndMemberEmail(memberSpecId, email);
    }

    @Override
    public Page<SpecReview> findBySpecId(Long specId, Pageable pageable) {
        return specReviewRepository.findBySpecId(specId, pageable);
    }

    @Override
    public boolean existsBySpecIdAndMemberEmailAndExamType(Long memberSpecId, String email, ExamType examType) {
        return specReviewRepository.existsByMemberSpecIdAndMemberEmailAndExamType(memberSpecId, email, examType);
    }
}
