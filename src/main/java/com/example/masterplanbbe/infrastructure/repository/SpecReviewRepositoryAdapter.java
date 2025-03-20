package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.common.exception.GlobalException;
import com.example.masterplanbbe.domain.repository.SpecReviewRepository;
import com.example.masterplanbbe.domain.repository.SpecReviewRepositoryPort;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.entity.SpecReview;
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
    public Page<SpecReview> findBySpec(Spec spec, Pageable pageable) {
        return specReviewRepository.findBySpec(spec, pageable);
    }

    @Override
    public SpecReview findByIdAndSpecId(Long id, Long specId) {
        return specReviewRepository.findByIdAndSpecId(id, specId);
    }

    @Override
    public boolean existsBySpecIdAndMemberEmail(Long specId, String email) {
        return specReviewRepository.existsBySpecIdAndMemberEmail(specId, email);
    }
}
