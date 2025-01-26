package com.example.masterplanbbe.domain.post.service;

import com.example.masterplanbbe.common.GlobalException;
import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.domain.post.dto.PostDto;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    /**
     *
     * @param postRequestDTO
     */
    @Transactional
    public PostDto.PostResponseDTO createPost(PostDto.PostRequestDTO postRequestDTO) {
        String title = postRequestDTO.getTitle();
        String content = postRequestDTO.getContent();

        if (title == null) {
            throw new GlobalException(ErrorCode.INVALID_INPUT_TITLE) {};
        }

        if (content == null) {
            throw new GlobalException(ErrorCode.INVALID_INPUT_CONTENT) {};
        }

        Post post = postRequestDTO.toEntity();
        postRepository.save(post);

        return PostDto.PostResponseDTO.builder()
                .post(post)
                .build();
    }

    /**
     *
     * @param postId
     * @return
     */
    public PostDto.PostResponseDTO getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_POST) {});

        return  PostDto.PostResponseDTO.builder()
                .post(post)
                .build();
    }

    public List<PostDto.PostResponseDTO> getAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(b -> PostDto.PostResponseDTO.builder()
                        .post(b)
                        .build())
                .toList();
    }

    /**
     *
     * @param postRequestDTO
     * @param postId
     * @return
     */
    public PostDto.PostResponseDTO updatePost(PostDto.PostRequestDTO postRequestDTO, Long postId) {
        String title = postRequestDTO.getTitle();
        String content = postRequestDTO.getContent();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_POST) {});

        post.updatePost(title, content);
        return PostDto.PostResponseDTO.builder()
                .post(post)
                .build();
    }

    /**
     *
     * @param postId
     */
    public void deletePost(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_POST) {});

        postRepository.deleteById(postId);
    }


}
