package com.example.masterplanbbe.domain.comment.service;

import com.example.masterplanbbe.common.GlobalException;
import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.domain.comment.dto.CommentDto;
import com.example.masterplanbbe.domain.comment.entity.Comment;
import com.example.masterplanbbe.domain.comment.repository.CommentRepositoryPort;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.domain.post.repository.PostRepositoryPort;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepositoryPort postRepositoryPort;

    private final CommentRepositoryPort commentRepositoryPort;

    private final MemberRepositoryPort memberRepositoryPort;


    /**
     * 댓글 생성
     * @param commentRequestDto
     * @param postId
     * @param memberId
     * @return
     */
    public CommentDto.CommentResponseDto createComment(Long postId, Long memberId, CommentDto.CommentRequestDto commentRequestDto) {

        Post post = postRepositoryPort.findById(postId);
        Member member = memberRepositoryPort.findById(memberId);

        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .member(member)
                .post(post)
                .build();

        commentRepositoryPort.save(comment);

        return CommentDto.CommentResponseDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .createAt(comment.getCreatedAt())
                .nickname(comment.getMember().getNickname())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }

    /**
     * 댓글 조회
     * @param postId
     * @return
     */
    public List<CommentDto.CommentResponseDto> findAllComment(Long postId) {
        Post post = postRepositoryPort.findById(postId);

        List<Comment> comments = commentRepositoryPort.findByPost(post);

        return comments.stream()
                .map(CommentDto.CommentResponseDto::new)
                .toList();
    }

    /**
     * 댓글 수정
     * @param commentRequestDto
     * @param commentId
     * @param memberId
     * @return
     */
    public CommentDto.CommentResponseDto updateComment( Long commentId, Long memberId, CommentDto.CommentRequestDto commentRequestDto) {
        Comment comment = commentRepositoryPort.findById(commentId);

        Member member = memberRepositoryPort.findById(memberId);

        if (!comment.getMember().getId().equals(member.getId())) {
            throw new GlobalException(ErrorCode.NOT_MODIFIED_COMMENT) {};
        }

        comment.updateComment(commentRequestDto.getContent());
        commentRepositoryPort.save(comment);


        return CommentDto.CommentResponseDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .nickname(comment.getMember().getNickname())
                .createAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();

    }

    /**
     * 댓글 삭제
     * @param postId
     * @param commentId
     * @param memberId
     */
    public void deleteComment(Long postId, Long commentId, Long memberId) {
        Post post = postRepositoryPort.findById(postId);

        Comment comment = commentRepositoryPort.findById(commentId);

        Member member = memberRepositoryPort.findById(memberId);

        if (!comment.getMember().getId().equals(member.getId())) {
            throw new GlobalException(ErrorCode.NOT_MODIFIED_COMMENT) {};
        }

        if (!comment.getPost().equals(post)) {
            throw new GlobalException(ErrorCode.NOT_BELONG_COMMENT) {};
        }

        commentRepositoryPort.delete(comment);
    }
}
