package com.example.masterplanbbe.domain.post.service;

import com.example.masterplanbbe.application.service.StoredPostService;
import com.example.masterplanbbe.domain.fixture.MemberFixture;
import com.example.masterplanbbe.presentation.response.PostResponse;
import com.example.masterplanbbe.domain.enums.Category;
import com.example.masterplanbbe.domain.entity.Post;
import com.example.masterplanbbe.domain.entity.StoredPost;
import com.example.masterplanbbe.infrastructure.repository.PostRepositoryAdapter;
import com.example.masterplanbbe.infrastructure.repository.StoredPostRepositoryAdapter;
import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.infrastructure.repository.MemberRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("게시글 북마크 서비스 테스트")
class StoredPostServiceTest {

    @InjectMocks
    private StoredPostService storedPostService;

    @Mock
    private StoredPostRepositoryAdapter storedPostRepositoryAdapter;

    @Mock
    private MemberRepositoryAdapter memberRepositoryAdapter;

    @Mock
    private PostRepositoryAdapter postRepositoryAdapter;

    private Member testMember;
    private Post testPost;
    private StoredPost storedPost;

    private final String testEmail = "test@example.com";

    @BeforeEach
    void setUp() {
        testMember = getMember();
        testPost = getPost(testMember);
        storedPost = new StoredPost(testMember, testPost);
    }

    @Test
    @DisplayName("게시글 북마크 추가")
    void addStorePost() {
        // Given
        when(memberRepositoryAdapter.findByEmail(testEmail)).thenReturn(testMember);
        when(postRepositoryAdapter.findById(1L)).thenReturn(testPost);
        when(storedPostRepositoryAdapter.existsByMemberAndPost(testMember, testPost)).thenReturn(false);

        // When
        PostResponse.Detail response = storedPostService.toggleStoredPost(testEmail, 1L);

        // Then
        assertThat(response.postId()).isEqualTo(1L);
        verify(storedPostRepositoryAdapter, times(1)).save(any(StoredPost.class));
    }

    @Test
    @DisplayName("게시글 북마크 삭제")
    void deleteStorePost() {
        // Given
        when(memberRepositoryAdapter.findByEmail(testEmail)).thenReturn(testMember);
        when(postRepositoryAdapter.findById(1L)).thenReturn(testPost);
        when(storedPostRepositoryAdapter.existsByMemberAndPost(testMember, testPost)).thenReturn(true);

        // When
        PostResponse.Detail response = storedPostService.toggleStoredPost(testEmail, 1L);

        // Then
        assertThat(response.postId()).isEqualTo(1L);
        verify(storedPostRepositoryAdapter, times(1)).deleteByMemberAndPost(testMember, testPost);
    }

    @Test
    @DisplayName("북마크 게시글 조회")
    void getStoredPost() {
        // Given
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        Page<StoredPost> storedPostPage = new PageImpl<>(List.of(storedPost), pageable, 1);

        when(memberRepositoryAdapter.findByEmail(testEmail)).thenReturn(testMember);
        when(storedPostRepositoryAdapter.findByMemberId(testMember.getId(), pageable)).thenReturn(storedPostPage);

        // When
        Page<PostResponse.Summary> response = storedPostService.getStoredPost(testEmail, pageable);

        // Then
        assertThat(response.getTotalElements()).isEqualTo(1);
        assertThat(response.getContent().get(0).postId()).isEqualTo(1L);
    }

    private static Member getMember() {
        Member member = MemberFixture.createMember();
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