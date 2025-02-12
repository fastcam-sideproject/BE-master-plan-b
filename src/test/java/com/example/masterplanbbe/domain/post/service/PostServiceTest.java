package com.example.masterplanbbe.domain.post.service;

import com.example.masterplanbbe.common.GlobalException;
import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.domain.comment.dto.CommentResponse;
import com.example.masterplanbbe.domain.post.dto.PostRequest;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("게시글 서비스 테스트")
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepositoryPort postRepositoryPort;

    @Mock
    private MemberRepositoryPort memberRepositoryPort;

    @Test
    @DisplayName("게시글 생성 성공")
    void createPost() {
        // Given
        Long memberId = 1L;
        Member member = getMember();

        PostRequest request = new PostRequest("Test Title", "Test Content",memberId);
        Post post = getPost(member);

        when(memberRepositoryPort.findById(memberId)).thenReturn(member);
        when(postRepositoryPort.save(any(Post.class))).thenReturn(post);

        // When
        PostResponse.Summary response = postService.createPost(memberId, request);

        // Then
        assertNotNull(response);
        assertEquals(post.getTitle(), response.title());
        assertEquals(post.getContent(), response.content());
    }



    @Test
    @DisplayName("게시글 조회 성공")
    void getPost() {
        // Given
        Long memberId = 1L;
        Member member = getMember();

        PostRequest request = new PostRequest("Test Title", "Test Content",memberId);
        Post post = getPost(member);

        when(postRepositoryPort.findById(post.getId())).thenReturn(post);

        // When
        PostResponse.Detail response = postService.getPost(1L);

        // Then
        assertNotNull(response);
        assertEquals(post.getTitle(), response.title());
        assertEquals(post.getContent(), response.content());
    }

    @Test
    @DisplayName("게시글 수정 성공")
    void updatePost() {
        // Given
        Long postId = 1L;
        Long memberId = 1L;
        Member member = getMember();
        Post post = getPost(member);
        PostRequest updatedRequest = new PostRequest("Updated Title", "Updated Content",memberId);

        when(postRepositoryPort.findById(postId)).thenReturn(post);
        when(memberRepositoryPort.findById(memberId)).thenReturn(member);
        when(postRepositoryPort.save(any(Post.class))).thenReturn(post);

        // When
        PostResponse.Detail response = postService.updatePost(postId, memberId, updatedRequest);

        // Then
        assertNotNull(response);
        assertEquals(updatedRequest.title(), response.title());
        assertEquals(updatedRequest.content(), response.content());
    }

    @Test
    @DisplayName("게시글 삭제 성공")
    void deletePost() {
        // Given
        Long postId = 1L;
        Long memberId = 1L;
        Member member = getMember();
        Post post = getPost(member);

        when(postRepositoryPort.findById(postId)).thenReturn(post);
        when(memberRepositoryPort.findById(memberId)).thenReturn(member);

        // When
        postService.deletePost(postId, memberId);

        // Then
        verify(postRepositoryPort, times(1)).delete(postId);
    }

    private static Member getMember() {
        Member member = Member.builder()
                .userId("user123")
                .email("test@example.com")
                .name("Test User")
                .nickname("TestNick")
                .password("password123")
                .phoneNumber("010-1234-5678")
                .birthday(LocalDate.of(1995, 5, 20))
                .profileImageUrl("http://image.url")
                .build();
        ReflectionTestUtils.setField(member, "id", 1L);
        return member;
    }

    private static Post getPost(Member member) {
        Post post = Post.builder()
                .title("Test Title")
                .content("Test Content")
                .member(member)
                .likeCount(0)
                .isLiked(false)
                .commentList(new ArrayList<>())
                .build();
        ReflectionTestUtils.setField(post, "id", 1L);
        return post;
    }
}