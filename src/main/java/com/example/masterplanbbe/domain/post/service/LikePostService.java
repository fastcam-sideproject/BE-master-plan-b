package com.example.masterplanbbe.domain.post.service;

import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.member.repository.MemberRepository;
import com.example.masterplanbbe.domain.member.repository.MemberRepositoryPort;
import com.example.masterplanbbe.domain.post.dto.PostResponse;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.domain.post.repository.PostRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikePostService {

    private final PostRepositoryPort postRepositoryPort;
    private final MemberRepositoryPort memberRepositoryPort;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String POST_LIKE_KEY = "post:like:";
    private static final String POST_LIKE_COUNT_KEY = "post:likeCount:";

    /**
     * 게시글 좋아요
     * @param postId
     * @param memberId
     * @return
     */
    @Transactional
    public PostResponse.Detail addLike(Long postId, String email) {
        Post post = postRepositoryPort.findById(postId);

        String postLikeKey = POST_LIKE_KEY + postId;
        String postLikeCountKey = POST_LIKE_COUNT_KEY + postId;
        String memberLikeKey = "member:likedPosts:" + email;

        Boolean isLiked = redisTemplate.opsForSet().isMember(postLikeKey, email);

        if (Boolean.TRUE.equals(isLiked)) {
            redisTemplate.opsForSet().remove(postLikeKey, email);
            redisTemplate.opsForSet().remove(memberLikeKey, postId.toString());
            redisTemplate.opsForValue().decrement(postLikeCountKey);
        } else {
            redisTemplate.opsForSet().add(postLikeKey, email);
            redisTemplate.opsForSet().add(memberLikeKey, postId.toString());
            redisTemplate.opsForValue().increment(postLikeCountKey);
            redisTemplate.expire(postLikeKey, 1, TimeUnit.DAYS);
            redisTemplate.expire(postLikeCountKey, 1, TimeUnit.DAYS);
        }

        // 최신 좋아요 수 조회
        Integer likeCount = redisTemplate.opsForValue().get(postLikeCountKey) != null
                ? Integer.parseInt(redisTemplate.opsForValue().get(postLikeCountKey))
                : post.getLikeCount();


        post.updateLikeCount(likeCount);
        postRepositoryPort.save(post);

        return PostResponse.Detail.from(post);
    }

    /**
     * 좋아요한 게시글 조회
     * @param memberId
     * @param pageable
     * @return
     */
    @Transactional
    public Page<PostResponse.Summary> getLikedPosts(String email, Pageable pageable) {
        String memberLikeKey = "member:likedPosts:" + email;

        Set<String> likedPostIds = redisTemplate.opsForSet().members(memberLikeKey);

        if (likedPostIds == null || likedPostIds.isEmpty()) {
            return Page.empty(pageable);
        }

        List<Long> postIdList = likedPostIds.stream().map(Long::valueOf).toList();
        Page<Post> posts = postRepositoryPort.findAllByIdIn(postIdList,pageable);


        return posts.map(PostResponse.Summary::from);
    }
}
