package com.example.masterplanbbe.domain.post.service;

import com.example.masterplanbbe.domain.fixture.MemberFixture;
import com.example.masterplanbbe.domain.post.dto.PostResponse;
import com.example.masterplanbbe.domain.post.entity.Category;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.domain.post.entity.StoredPost;
import com.example.masterplanbbe.domain.post.repository.PostRepositoryAdapter;
import com.example.masterplanbbe.domain.post.repository.StoredPostRepositoryAdapter;
import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.member.repository.MemberRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;

import static com.example.masterplanbbe.domain.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("게시글 북마크 서비스 테스트")
class StoredPostServiceTest {

    @InjectMocks
    StoredPostService storedPostService;

    @Mock
    StoredPostRepositoryAdapter storedPostRepositoryAdapter;
    @Mock
    MemberRepositoryAdapter memberRepositoryAdapter;
    @Mock
    PostRepositoryAdapter postRepositoryAdapter;

    private Member testMember;
    private Post testPost;
    private StoredPost storedPost;

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
        when(memberRepositoryAdapter.findById(1L)).thenReturn(testMember);
        when(postRepositoryAdapter.findById(1L)).thenReturn(testPost);
        when(storedPostRepositoryAdapter.existsByMemberAndPost(testMember, testPost)).thenReturn(false);

        // When
        PostResponse.Detail response = storedPostService.toggleStoredPost(1L, 1L);

        // Then
        assertThat(response.postId()).isEqualTo(1L);
        verify(storedPostRepositoryAdapter, times(1)).save(any(StoredPost.class));
    }

    @Test
    @DisplayName("게시글 북마크 삭제")
    void deleteStorePost() {
        // Given
        when(memberRepositoryAdapter.findById(1L)).thenReturn(testMember);
        when(postRepositoryAdapter.findById(1L)).thenReturn(testPost);
        when(storedPostRepositoryAdapter.existsByMemberAndPost(testMember, testPost)).thenReturn(true);

        // When
        PostResponse.Detail response = storedPostService.toggleStoredPost(1L, 1L);

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
        when(storedPostRepositoryAdapter.findByMemberId(1L, pageable)).thenReturn(storedPostPage);

        // When
        Page<PostResponse.Summary> response = storedPostService.getStoredPost(1L, pageable);

        // Then
        assertThat(response.getTotalElements()).isEqualTo(1);
        assertThat(response.getContent().get(0).postId()).isEqualTo(1L);
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