package com.example.masterplanbbe.domain.post.service;

import com.example.masterplanbbe.application.service.PostService;
import com.example.masterplanbbe.presentation.request.PostRequest;
import com.example.masterplanbbe.presentation.response.PostResponse;
import com.example.masterplanbbe.domain.enums.Category;
import com.example.masterplanbbe.domain.entity.Post;
import com.example.masterplanbbe.domain.repository.PostRepositoryPort;
import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.repository.MemberRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static com.example.masterplanbbe.domain.fixture.MemberFixture.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        String email = "test@naver.com";
        Member member = getMember();

        PostRequest request = new PostRequest(Category.TIP,"Test Title", "Test Content");
        Post post = getPost(member);

        when(memberRepositoryPort.findByEmail(email)).thenReturn(member);
        when(postRepositoryPort.save(any(Post.class))).thenReturn(post);

        // When
        PostResponse.Summary response = postService.createPost(email, request);

        // Then
        assertNotNull(response);
        assertEquals(post.getTitle(), response.title());
        assertEquals(post.getContent(), response.content());
    }



    @Test
    @DisplayName("게시글 조회 성공")
    void getPost() {
        // Given
        String email = "test@naver.com";
        Member member = getMember();

        PostRequest request = new PostRequest(Category.TIP,"Test Title", "Test Content");
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
        String email = "test@naver.com";
        Member member = getMember();
        Post post = getPost(member);
        PostRequest updatedRequest = new PostRequest(Category.TIP,"Updated Title", "Updated Content");

        when(postRepositoryPort.findById(postId)).thenReturn(post);
        when(memberRepositoryPort.findByEmail(email)).thenReturn(member);
        when(postRepositoryPort.save(any(Post.class))).thenReturn(post);

        // When
        PostResponse.Detail response = postService.updatePost(postId, email, updatedRequest);

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
        String email = "test@naver.com";
        Member member = getMember();
        Post post = getPost(member);

        when(postRepositoryPort.findById(postId)).thenReturn(post);
        when(memberRepositoryPort.findByEmail(email)).thenReturn(member);

        // When
        postService.deletePost(postId, email);

        // Then
        verify(postRepositoryPort, times(1)).delete(postId);
    }

    private static Member getMember() {
        Member member = createMember();
        ReflectionTestUtils.setField(member, "id", 1L);
        return member;
    }

    private static Post getPost(Member member) {
        Post post = Post.builder()
                .category(Category.TIP)
                .title("Test Title")
                .content("Test Content")
                .member(member)
                .build();
        ReflectionTestUtils.setField(post, "id", 1L);
        return post;
    }
}