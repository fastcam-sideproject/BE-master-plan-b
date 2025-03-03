package com.example.masterplanbbe.domain.specReview.serivce;

import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.common.exception.GlobalException;
import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.member.repository.MemberRepositoryAdapter;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.spec.repository.SpecRepository;
import com.example.masterplanbbe.domain.spec.repository.SpecRepositoryAdapter;
import com.example.masterplanbbe.domain.specReview.dto.SpecReviewRequest;
import com.example.masterplanbbe.domain.specReview.dto.SpecReviewResponse;
import com.example.masterplanbbe.domain.specReview.entity.SpecReview;
import com.example.masterplanbbe.domain.specReview.repository.SpecReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpecReviewService {
    private final SpecReviewRepository specReviewRepository;
    private final SpecRepository specRepository;
    private final MemberRepositoryAdapter memberRepositoryAdapter;

    /**
     * 리뷰 작성하기
     * @param specReviewRequest
     * @return
     */
    public SpecReviewResponse addReview(SpecReviewRequest specReviewRequest) {
        Spec spec = specRepository.getById(specReviewRequest.specId());
        Member member = memberRepositoryAdapter.findById(specReviewRequest.memberId());
        SpecReview specReview = specReviewRequest.toEntity(member, spec);
        SpecReview saved = specReviewRepository.save(specReview);

        return SpecReviewResponse.from(saved);
    }

    /**
     * 리뷰 단일 확인
     * @param specId
     * @return
     */
    public SpecReviewResponse getReview(Long specId) {
        Spec spec = specRepository.getById(specId);
        SpecReview specreview = specReviewRepository.findBySpec(spec);

        return SpecReviewResponse.from(specreview);
    }

    /**
     * 리뷰 전체 가져오기
     * @param specId
     * @param pageable
     * @return
     */
    public Page<SpecReviewResponse> getAllReview(Long specId, Pageable pageable) {
        Spec spec = specRepository.getById(specId);
        Page<SpecReview> reviewPage = specReviewRepository.findBySpec(spec, pageable);

        return reviewPage.map(SpecReviewResponse::from);
    }

    /**
     * 리뷰 삭제
     * @param reviewId
     * @param memberId
     */
    public void deleteReview(Long reviewId, Long memberId) {
        SpecReview specReview = specReviewRepository.findById(reviewId)
                .orElseThrow(() -> new GlobalException.NotFoundException(ErrorCode.NOT_FOUND_REVIEW));

        Member member = memberRepositoryAdapter.findById(memberId);

        if (!specReview.getMember().getId().equals(member.getId())) {
            throw new GlobalException.BadRequestException(ErrorCode.NOT_DELETE_REVIEW);
        }

        specReviewRepository.delete(specReview);
    }

//    public SpecReviewResponse updateReview(SpecReviewRequest specReviewRequest, Long memberId) {
//
//    }
}
