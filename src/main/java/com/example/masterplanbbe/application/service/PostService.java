package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.infrastructure.exception.GlobalException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;
import com.example.masterplanbbe.presentation.response.CommentResponse;
import com.example.masterplanbbe.presentation.request.PostRequest;
import com.example.masterplanbbe.presentation.response.PostResponse;
import com.example.masterplanbbe.domain.entity.Post;
import com.example.masterplanbbe.domain.repository.PostRepositoryPort;
import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.enums.MemberRoleEnum;
import com.example.masterplanbbe.domain.repository.MemberRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public PostResponse.Summary createPost(String email, PostRequest postRequestDTO) {

        Member member = memberRepositoryPort.findByEmail(email);
        String title = postRequestDTO.title();
        String content = postRequestDTO.content();

        if (title == null) {
            throw new GlobalException(ErrorCode.INVALID_INPUT_TITLE) {};
        }

        if (content == null) {
            throw new GlobalException(ErrorCode.INVALID_INPUT_CONTENT) {};
        }

        Post post = postRequestDTO.toEntity(member);
        postRepositoryPort.save(post);

        return PostResponse.Summary.from(post);
    }

    /**
     * 게시글 확인
     * @param postId
     * @return
     */
    @Transactional
    public PostResponse.Detail getPost(Long postId) {
        Post post = postRepositoryPort.findById(postId);
        post.addViewCount();
        postRepositoryPort.save(post);
        return PostResponse.Detail.from(post);
    }

    /**
     * 전체 게시글 조회
     * @return
     */
    public Page<PostResponse.Summary> getAllPost(Pageable pageable) {
        return postRepositoryPort.findAll(pageable)
                .map(PostResponse.Summary::from);
    }

    /**
     * 게시글 수정
     * @param postId
     * @param memberId
     * @param postRequestDTO
     * @return
     */
    public PostResponse.Detail updatePost(Long postId, String email, PostRequest postRequestDTO) {
        String title = postRequestDTO.title();
        String content = postRequestDTO.content();

        Post post = postRepositoryPort.findById(postId);
        Member member = memberRepositoryPort.findByEmail(email);

        if (!post.getMember().getId().equals(member.getId())) {
            throw new GlobalException(ErrorCode.NOT_MODIFIED_POST) {};
        }

        List<CommentResponse> commentList = post.getCommentList().stream()
                .map(CommentResponse::from)
                .toList();

        post.updatePost(title, content);
        postRepositoryPort.save(post);

        return PostResponse.Detail.from(post);
    }

    /**
     * 게시글 삭제
     * @param postId
     * @param memberId
     */
    public void deletePost(Long postId, String email) {
        Post post = postRepositoryPort.findById(postId);

        Member member = memberRepositoryPort.findByEmail(email);

        if (!post.getMember().getId().equals(member.getId()) && member.getRole() != MemberRoleEnum.ADMIN) {
            throw new GlobalException(ErrorCode.NOT_DELETED_POST) {};
        }

        postRepositoryPort.deleteById(postId);
    }

    /**
     * 게시글 검색
     * @param query
     * @return
     */
    public Page<PostResponse.Summary> searchPost(String query, Pageable pageable) {
        return postRepositoryPort.findByTitleContaining(query, pageable)
                .map(PostResponse.Summary::from);
    }

    /**
     * 내 게시글 조회
     * @param memberId
     * @param pageable
     * @return
     */
    public Page<PostResponse.Summary> getMyPost(String email, Pageable pageable) {
        Member member = memberRepositoryPort.findByEmail(email);
        Long memberId = member.getId();

        return postRepositoryPort.findByMemberId(memberId, pageable)
                .map(PostResponse.Summary::from);
    }

}
