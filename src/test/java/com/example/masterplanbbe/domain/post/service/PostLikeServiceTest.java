package com.example.masterplanbbe.domain.post.service;

import com.example.masterplanbbe.domain.post.dto.PostResponse;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.domain.post.repository.PostRepositoryPort;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("게시글 좋아요 테스트")
public class PostLikeServiceTest {
    @InjectMocks
    PostLikeService likeService;

    @Mock
    PostRepositoryPort postRepositoryPort;

    @Mock
    MemberRepositoryPort memberRepositoryPort;

    @Mock
    RedisTemplate<String, String> redisTemplate;

    @Mock
    private SetOperations<String, String> setOperations;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @Test
    @DisplayName("게시글 좋아요 추가")
    void addLike_first() {
        // Given
        Long postId = 1L;
        Long memberId = 1L;
        Member member = Member.builder()
                .email("test@naver.com")
                .name("테스트")
                .userId("test")
                .password("test")
                .build();
        when(memberRepositoryPort.findById(memberId)).thenReturn(member);

        Post post = Post.builder()
                .content("test Content")
                .title("test title")
                .member(member)
                .build();

        when(postRepositoryPort.findById(postId)).thenReturn(post);

        String postLikeKey = "post:like:" + postId;
        String postLikeCountKey = "post:likeCount:" + postId;

        when(redisTemplate.opsForSet()).thenReturn(setOperations);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(setOperations.isMember(postLikeKey, memberId.toString())).thenReturn(false);

        when(valueOperations.increment(postLikeCountKey)).thenReturn(1L);
        when(valueOperations.get(postLikeCountKey)).thenReturn("1");

        // When
        PostResponse.Detail response = likeService.addLike(postId, memberId);

        // Then
        assertEquals(1, response.likeCount());
        verify(setOperations).add(postLikeKey, memberId.toString());
        verify(valueOperations).increment(postLikeCountKey);
        verify(redisTemplate).expire(postLikeKey, 1, TimeUnit.DAYS);
        verify(redisTemplate).expire(postLikeCountKey, 1, TimeUnit.DAYS);
    }
    
    @Test
    @DisplayName("게시글 좋아요 이미 있을 경우")
    void addLike_alreadyLike() {
        // Given
        // Given
        Long postId = 1L;
        Long memberId = 1L;
        Member member = Member.builder()
                .email("test@naver.com")
                .name("테스트")
                .userId("test")
                .password("test")
                .build();
        when(memberRepositoryPort.findById(memberId)).thenReturn(member);

        Post post = Post.fullBuilder()
                .content("test Content")
                .title("test title")
                .member(member)
                .likeCount(5)
                .build();

        when(postRepositoryPort.findById(postId)).thenReturn(post);

        String postLikeKey = "post:like:" + postId;
        String postLikeCountKey = "post:likeCount:" + postId;
        when(redisTemplate.opsForSet()).thenReturn(setOperations);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(setOperations.isMember(postLikeKey, memberId.toString())).thenReturn(true);
        when(valueOperations.get(postLikeCountKey)).thenReturn("4");

        // When
        PostResponse.Detail response = likeService.addLike(postId, memberId);

        // Then
        assertEquals(4, response.likeCount());
        verify(setOperations).remove(postLikeKey, memberId.toString());
        verify(valueOperations).decrement(postLikeCountKey);
    }
}
