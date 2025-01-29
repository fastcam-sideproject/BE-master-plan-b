package com.example.masterplanbbe.domain.comment.service;

import com.example.masterplanbbe.common.GlobalException;
import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.domain.comment.dto.CommentDto;
import com.example.masterplanbbe.domain.comment.entity.Comment;
import com.example.masterplanbbe.domain.comment.repository.CommentRepository;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    // private final UserRepository userRepository;
    // TODO: 2025/01/29 User 갱신이후 시작 - Nano


    /**
     * 댓글 생성
     * @param commentRequestDto
     * @param postId
     * @return
     */
    public CommentDto.CommentResponseDto createComment(CommentDto.CommentRequestDto commentRequestDto, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_POST) {});

        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .post(post)
                .build();

        commentRepository.save(comment);

        return new CommentDto.CommentResponseDto(comment);
    }

    /**
     * 댓글 조회
     * @param postId
     * @return
     */
    public List<CommentDto.CommentResponseDto> findAllComment(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_POST) {});

        List<Comment> comments = commentRepository.findByPost(post);

        return comments.stream()
                .map(CommentDto.CommentResponseDto::new)
                .toList();
    }

    /**
     * 댓글 수정
     * @param commentRequestDto
     * @param commentId
     * @return
     */
    public CommentDto.CommentResponseDto updateComment(CommentDto.CommentRequestDto commentRequestDto, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_COMMENT) {});

        comment.updateComment(commentRequestDto.getContent());
        commentRepository.save(comment);

        return new CommentDto.CommentResponseDto(comment);

    }

    /**
     * 댓글 삭제
     * @param postId
     * @param commentId
     */
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_POST) {});

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_COMMENT) {});

        if (!comment.getPost().equals(post)) {
            throw new GlobalException(ErrorCode.NOT_BELONG_COMMENT) {};
        }

        commentRepository.delete(comment);
    }
}
