package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.infrastructure.exception.GlobalException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;
import com.example.masterplanbbe.presentation.request.CommentRequest;
import com.example.masterplanbbe.presentation.response.CommentResponse;
import com.example.masterplanbbe.domain.entity.Comment;
import com.example.masterplanbbe.domain.repository.CommentRepositoryPort;
import com.example.masterplanbbe.domain.entity.Post;
import com.example.masterplanbbe.domain.repository.PostRepositoryPort;
import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.enums.MemberRoleEnum;
import com.example.masterplanbbe.domain.repository.MemberRepositoryPort;
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
    public CommentResponse createComment(Long postId, String email, CommentRequest commentRequestDto) {

        Post post = postRepositoryPort.findById(postId);
        Member member = memberRepositoryPort.findByEmail(email);

        Comment comment = Comment.builder()
                .content(commentRequestDto.content())
                .member(member)
                .post(post)
                .build();

        commentRepositoryPort.save(comment);

        return CommentResponse.from(comment);
    }

    /**
     * 댓글 조회
     * @param postId
     * @return
     */
    public List<CommentResponse> findAllComment(Long postId) {
        Post post = postRepositoryPort.findById(postId);

        List<Comment> comments = commentRepositoryPort.findByPost(post);

        return comments.stream()
                .map(CommentResponse::from)
                .toList();
    }

    /**
     * 댓글 수정
     * @param commentRequestDto
     * @param commentId
     * @param memberId
     * @return
     */
    public CommentResponse updateComment( Long commentId, String email, CommentRequest commentRequestDto) {
        Comment comment = commentRepositoryPort.findById(commentId);

        Member member = memberRepositoryPort.findByEmail(email);

        if (!comment.getMember().getId().equals(member.getId())) {
            throw new GlobalException(ErrorCode.NOT_MODIFIED_COMMENT) {};
        }

        comment.updateComment(commentRequestDto.content());
        commentRepositoryPort.save(comment);


        return CommentResponse.from(comment);
    }

    /**
     * 댓글 삭제
     * @param postId
     * @param commentId
     * @param memberId
     */
    public void deleteComment(Long postId, Long commentId, String email) {
        Post post = postRepositoryPort.findById(postId);

        Comment comment = commentRepositoryPort.findById(commentId);

        Member member = memberRepositoryPort.findByEmail(email);

        if (!comment.getMember().getId().equals(member.getId()) && member.getRole() != MemberRoleEnum.ADMIN) {
            throw new GlobalException(ErrorCode.NOT_DELETED_COMMENT) {};
        }

        if (!comment.getPost().equals(post)) {
            throw new GlobalException(ErrorCode.NOT_BELONG_COMMENT) {};
        }

        commentRepositoryPort.delete(comment);
    }
}
