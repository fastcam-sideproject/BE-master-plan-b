package com.example.masterplanbbe.domain.post.service;

import com.example.masterplanbbe.application.service.LikePostService;
import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.repository.MemberRepositoryPort;
import com.example.masterplanbbe.presentation.response.PostResponse;
import com.example.masterplanbbe.domain.entity.Post;
import com.example.masterplanbbe.domain.repository.PostRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.TimeUnit;

import static com.example.masterplanbbe.domain.fixture.MemberFixture.createMember;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("게시글 좋아요 테스트")
public class LikePostServiceTest {
    @InjectMocks
    LikePostService likeService;

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
        String email = "test@naver.com";
        Member member = getMember();

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
        when(setOperations.isMember(postLikeKey, email)).thenReturn(false);

        when(valueOperations.increment(postLikeCountKey)).thenReturn(1L);
        when(valueOperations.get(postLikeCountKey)).thenReturn("1");

        // When
        PostResponse.Detail response = likeService.addLike(postId, email);

        // Then
        assertEquals(1, response.likeCount());
        verify(setOperations).add(postLikeKey, email);
        verify(valueOperations).increment(postLikeCountKey);
        verify(redisTemplate).expire(postLikeKey, 1, TimeUnit.DAYS);
        verify(redisTemplate).expire(postLikeCountKey, 1, TimeUnit.DAYS);
    }

    @Test
    @DisplayName("게시글 좋아요 이미 있을 경우")
    void addLike_alreadyLike() {
        // Given
        Long postId = 1L;
        String email = "test@naver.com";
        Member member = getMember();

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
        when(setOperations.isMember(postLikeKey, email)).thenReturn(true);
        when(valueOperations.get(postLikeCountKey)).thenReturn("4");

        // When
        PostResponse.Detail response = likeService.addLike(postId, email);

        // Then
        assertEquals(4, response.likeCount());
        verify(setOperations).remove(postLikeKey, email);
        verify(valueOperations).decrement(postLikeCountKey);
    }

    private static Member getMember() {
        Member member = createMember();
        ReflectionTestUtils.setField(member, "id", 1L);
        return member;
    }
}
