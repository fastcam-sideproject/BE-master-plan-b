package com.example.masterplanbbe.domain.service;

import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.entity.Spec;
import com.example.masterplanbbe.domain.repository.SpecRepository;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;
import com.example.masterplanbbe.infrastructure.exception.GlobalException;
import com.example.masterplanbbe.infrastructure.repository.MemberRepositoryAdapter;
import com.example.masterplanbbe.presentation.request.SpecReviewRequest;
import com.example.masterplanbbe.presentation.response.SpecReviewResponse;
import com.example.masterplanbbe.domain.entity.SpecReview;
import com.example.masterplanbbe.infrastructure.repository.SpecReviewRepositoryAdapter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpecReviewService {
    private final SpecReviewRepositoryAdapter specReviewRepositoryAdapter;
    private final SpecRepository specRepository;
    private final MemberRepositoryAdapter memberRepositoryAdapter;

    /**
     * 리뷰 작성하기
     * @param specReviewRequest
     * @param specId
     * @param memberId
     * @return
     */
    public SpecReviewResponse addReview(SpecReviewRequest specReviewRequest,Long specId, String email) {
        if (specReviewRepositoryAdapter.existsBySpecIdAndMemberEmail(specId, email)) {
            throw new GlobalException.BadRequestException(ErrorCode.ALREADY_CREATE_REVIEW);
        }

        Spec spec = specRepository.getById(specId);
        Member member = memberRepositoryAdapter.findByEmail(email);
        SpecReview specReview = specReviewRequest.toEntity(member, spec);
        SpecReview saved = specReviewRepositoryAdapter.save(specReview);

        return SpecReviewResponse.from(saved);
    }

    /**
     * 리뷰 단일 확인
     * @param specId
     * @param specReviewId
     * @return
     */
    public SpecReviewResponse getReview(Long specId, Long specReviewId) {
        SpecReview specReview = specReviewRepositoryAdapter.findByIdAndSpecId(specReviewId, specId);
        specReview.addViewCount();
        specReviewRepositoryAdapter.save(specReview);
        return SpecReviewResponse.from(specReview);
    }

    /**
     * 리뷰 전체 가져오기
     * @param specId
     * @param pageable
     * @return
     */
    public Page<SpecReviewResponse> getAllReview(Long specId, Pageable pageable) {
        Spec spec = specRepository.getById(specId);
        Page<SpecReview> reviewPage = specReviewRepositoryAdapter.findBySpec(spec, pageable);

        return reviewPage.map(SpecReviewResponse::from);
    }

    /**
     * 리뷰 삭제
     * @param specId
     * @param reviewId
     * @param memberId
     */
    public void deleteReview(Long specId, Long specReviewId, String email) {
        SpecReview specReview = specReviewRepositoryAdapter.findByIdAndSpecId(specReviewId, specId);

        Member member = memberRepositoryAdapter.findByEmail(email);

        if (!specReview.getMember().getId().equals(member.getId())) {
            throw new GlobalException.BadRequestException(ErrorCode.NOT_DELETE_REVIEW);
        }

        specReviewRepositoryAdapter.deleteById(specReviewId);
    }

    /**
     * 리뷰 수정
     * @param specReviewRequest
     * @param memberId
     * @param specReviewId
     * @param specId
     * @return
     */
    public SpecReviewResponse updateReview(SpecReviewRequest specReviewRequest, String email, Long specReviewId, Long specId) {
        SpecReview specReview = specReviewRepositoryAdapter.findByIdAndSpecId(specReviewId, specId);

        if (!specReview.getMember().getEmail().equals(email)) {
            throw new GlobalException.BadRequestException(ErrorCode.NOT_MODIFIED_REVIEW);
        }

        specReview.updateReview(specReviewRequest);
        specReviewRepositoryAdapter.save(specReview);

        return SpecReviewResponse.from(specReview);
    }
}
