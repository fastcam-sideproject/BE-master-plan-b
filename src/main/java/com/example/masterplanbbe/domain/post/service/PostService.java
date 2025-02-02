package com.example.masterplanbbe.domain.post.service;

import com.example.masterplanbbe.common.GlobalException;
import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.domain.comment.dto.CommentDto;
import com.example.masterplanbbe.domain.post.dto.PostDto;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.domain.post.repository.PostRepositoryPort;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepositoryPort postRepositoryPort;
    private final MemberRepositoryPort memberRepositoryPort;

    /**
     * 게시글 생성
     * @param memberId 
     * @param postRequestDTO
     */
    @Transactional
    public PostDto.PostResponseDTO createPost(Long memberId, PostDto.PostRequestDTO postRequestDTO) {

        Member member = memberRepositoryPort.findById(memberId);

        String title = postRequestDTO.getTitle();
        String content = postRequestDTO.getContent();

        if (title == null) {
            throw new GlobalException(ErrorCode.INVALID_INPUT_TITLE) {};
        }

        if (content == null) {
            throw new GlobalException(ErrorCode.INVALID_INPUT_CONTENT) {};
        }

        Post post = postRequestDTO.toEntity(member);
        postRepositoryPort.save(post);

        return PostDto.PostResponseDTO.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .nickname(post.getMember().getNickname())
                .build();
    }

    /**
     * 게시글 확인
     * @param postId
     * @return
     */
    public PostDto.PostResponseDTO getPost(Long postId) {
        Post post = postRepositoryPort.findById(postId);
        List<CommentDto.CommentResponseDto> commentList = post.getCommentList().stream()
                .map(CommentDto.CommentResponseDto::new)
                .toList();

        return PostDto.PostResponseDTO.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .comments(commentList)
                .nickname(post.getMember().getNickname())
                .build();
    }

    /**
     * 전체 게시글 조회
     * @return
     */
    public List<PostDto.PostResponseDTO> getAllPost() {
        List<Post> posts = postRepositoryPort.findAll();
        return posts.stream()
                .map(post -> PostDto.PostResponseDTO.builder()
                        .postId(post.getId())
                        .content(post.getContent())
                        .title(post.getTitle())
                        .createAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .nickname(post.getMember().getNickname())
                        .build())
                .toList();
    }

    /**
     * 게시글 수정
     * @param postId
     * @param memberId
     * @param postRequestDTO
     * @return
     */
    public PostDto.PostResponseDTO updatePost(Long postId, Long memberId, PostDto.PostRequestDTO postRequestDTO) {
        String title = postRequestDTO.getTitle();
        String content = postRequestDTO.getContent();

        Post post = postRepositoryPort.findById(postId);
        Member member = memberRepositoryPort.findById(memberId);

        if (!post.getMember().getId().equals(member.getId())) {
            throw new GlobalException(ErrorCode.NOT_MODIFIED_POST) {};
        }

        post.updatePost(title, content);
        postRepositoryPort.save(post);

        return PostDto.PostResponseDTO.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .nickname(post.getMember().getNickname())
                .build();
    }

    /**
     * 게시글 삭제
     * @param postId
     * @param memberId
     */
    public void deletePost(Long postId, Long memberId) {
        Post post = postRepositoryPort.findById(postId);

        Member member = memberRepositoryPort.findById(memberId);
        if (!post.getMember().getId().equals(member.getId())) {
            throw new GlobalException(ErrorCode.NOT_DELETED_POST) {};
        }

        postRepositoryPort.delete(postId);
    }


}
