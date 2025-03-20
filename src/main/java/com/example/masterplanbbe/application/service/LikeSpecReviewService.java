package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.presentation.response.SpecReviewResponse;
import com.example.masterplanbbe.domain.entity.SpecReview;
import com.example.masterplanbbe.domain.repository.SpecReviewRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class LikeSpecReviewService {

    private final SpecReviewRepositoryPort specReviewRepositoryPort;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String REVIEW_LIKE_KEY = "review:like:";
    private static final String REVIEW_LIKE_COUNT_KEY = "review:likeCount:";

    /**
     * 게시글 좋아요
     * @param postId
     * @param memberId
     * @return
     */
    @Transactional
    public SpecReviewResponse addLike(Long reviewId, String email) {
        SpecReview review = specReviewRepositoryPort.findById(reviewId);

        String reviewLikeKey = REVIEW_LIKE_KEY + reviewId;
        String reviewLikeCountKey = REVIEW_LIKE_COUNT_KEY + reviewId;
        String memberLikeKey = "member:likedPosts:" + email;

        Boolean isLiked = redisTemplate.opsForSet().isMember(reviewLikeKey, email);

        if (Boolean.TRUE.equals(isLiked)) {
            redisTemplate.opsForSet().remove(reviewLikeKey, email);
            redisTemplate.opsForSet().remove(memberLikeKey, reviewId.toString());
            redisTemplate.opsForValue().decrement(reviewLikeCountKey);
        } else {
            redisTemplate.opsForSet().add(reviewLikeKey, email);
            redisTemplate.opsForSet().add(memberLikeKey, reviewId.toString());
            redisTemplate.opsForValue().increment(reviewLikeCountKey);
            redisTemplate.expire(reviewLikeKey, 1, TimeUnit.DAYS);
            redisTemplate.expire(reviewLikeCountKey, 1, TimeUnit.DAYS);
        }

        // 최신 좋아요 수 조회
        Integer likeCount = redisTemplate.opsForValue().get(reviewLikeCountKey) != null
                ? Integer.parseInt(redisTemplate.opsForValue().get(reviewLikeCountKey))
                : review.getLikeCount();


        review.updateLikeCount(likeCount);
        specReviewRepositoryPort.save(review);

        return SpecReviewResponse.from(review);
    }

}
