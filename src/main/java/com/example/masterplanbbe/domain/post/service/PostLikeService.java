package com.example.masterplanbbe.domain.post.service;

import com.example.masterplanbbe.domain.post.dto.PostResponse;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.domain.post.repository.PostRepositoryPort;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final MemberRepositoryPort memberRepositoryPort;
    private final PostRepositoryPort postRepositoryPort;

    private final RedisTemplate<String, String> redisTemplate;

    private static final String POST_LIKE_KEY = "post:like:";
    private static final String POST_LIKE_COUNT_KEY = "post:likeCount:";

    @Transactional
    public PostResponse.Detail addLike(Long postId, Long memberId) {
        Member member = memberRepositoryPort.findById(memberId);
        Post post = postRepositoryPort.findById(postId);

        String postLikeKey = POST_LIKE_KEY + postId;
        String postLikeCountKey = POST_LIKE_COUNT_KEY + postId;

        Boolean isLiked = redisTemplate.opsForSet().isMember(postLikeKey, memberId.toString());

        if (Boolean.TRUE.equals(isLiked)) {
            redisTemplate.opsForSet().remove(postLikeKey, memberId.toString());
            redisTemplate.opsForValue().decrement(postLikeCountKey);
        } else {
            redisTemplate.opsForSet().add(postLikeKey, memberId.toString());
            redisTemplate.opsForValue().increment(postLikeCountKey);
            redisTemplate.expire(postLikeKey, 1, TimeUnit.DAYS);
            redisTemplate.expire(postLikeCountKey, 1, TimeUnit.DAYS);
        }

        // 최신 좋아요 수 조회
        Integer likeCount = redisTemplate.opsForValue().get(postLikeCountKey) != null
                ? Integer.parseInt(redisTemplate.opsForValue().get(postLikeCountKey))
                : post.getLikeCount();

        return PostResponse.Detail.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(likeCount)
                .nickname(post.getMember().getNickname())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }
}
