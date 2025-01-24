package com.example.masterplanbbe.domain.post.service;

import com.example.masterplanbbe.common.GlobalException;
import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.domain.post.dto.PostDto;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.domain.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("게시글 서비스 테스트")
class PostServiceTest {

    @InjectMocks
    PostService postService;

    @Mock
    PostRepository postRepository;

    @Test
    @DisplayName("게시글 생성 성공")
    void createPost() {
        // Given
        PostDto.PostRequestDTO requestDTO = createRequestDTO();
        Post post = requestDTO.toEntity();
        when(postRepository.save(any(Post.class))).thenReturn(post);

        // When
        PostDto.PostResponseDTO responseDTO = postService.createPost(requestDTO);

        // Then
        assertEquals(post.getTitle(), responseDTO.getTitle());
        assertEquals(post.getNickname(), responseDTO.getNickname());
    }

    @Test
    @DisplayName("게시글 생성 실패 - 제목이 null")
    void createPostNullTitle() {
        // Given
        PostDto.PostRequestDTO requestDTO = createRequestDTO();

        // When & Then
        GlobalException exception = assertThrows(GlobalException.class, () -> postService.createPost(requestDTO));
        assertEquals(ErrorCode.INVALID_INPUT_TITLE, exception.getErrorCode());
    }

    @Test
    @DisplayName("게시글 생성 실패 - 내용이 null")
    void createPostNullContent() {
        // Given
        PostDto.PostRequestDTO requestDTO = createRequestDTO();

        // When & Then
        GlobalException exception = assertThrows(GlobalException.class, () -> postService.createPost(requestDTO));
        assertEquals(ErrorCode.INVALID_INPUT_CONTENT, exception.getErrorCode());
    }

    @Test
    @DisplayName("게시글 조회 성공")
    void getPost() {
        // Given
        PostDto.PostRequestDTO requestDTO = createRequestDTO();
        Post post = requestDTO.toEntity();
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        // When
        PostDto.PostResponseDTO responseDTO = postService.getPost(post.getId());

        // Then
        assertNotNull(responseDTO);
        assertEquals(post.getTitle(), responseDTO.getTitle());
        assertEquals(post.getContent(), responseDTO.getContent());
        assertEquals(post.getNickname(), responseDTO.getNickname());
    }

    @Test
    @DisplayName("게시글 수정 성공")
    void updatePost() {
        // Given
        PostDto.PostRequestDTO requestDTO = createRequestDTO();
        PostDto.PostRequestDTO requestDTO2 = new PostDto.PostRequestDTO("Title2", "Content2", "test2");
        Post post = requestDTO.toEntity();
        when(postRepository.save(post)).thenReturn(post);

        PostDto.PostResponseDTO responseDTO = postService.createPost(requestDTO);

        // When
    	postService.updatePost(requestDTO2)

        // Then

    }

    private PostDto.PostRequestDTO createRequestDTO() {
        return new PostDto.PostRequestDTO("Test Title", "Test Content", "test");
    }

}