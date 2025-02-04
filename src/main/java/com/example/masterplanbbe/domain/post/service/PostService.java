package com.example.masterplanbbe.domain.post.service;

import com.example.masterplanbbe.common.GlobalException;
import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.domain.comment.dto.CommentResponse;
import com.example.masterplanbbe.domain.post.dto.PostRequest;
import com.example.masterplanbbe.domain.post.dto.PostResponse;
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
    public PostResponse.Summary createPost(Long memberId, PostRequest postRequestDTO) {

        Member member = memberRepositoryPort.findById(memberId);

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

        return PostResponse.Summary.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .nickname(post.getMember().getNickname())
                .build();
    }

    /**
     * 게시글 확인
     * @param postId
     * @return
     */
    public PostResponse.Detail getPost(Long postId) {
        Post post = postRepositoryPort.findById(postId);
        List<CommentResponse> commentList = post.getCommentList().stream()
                .map(CommentResponse::from)
                .toList();

        return PostResponse.Detail.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .comments(commentList)
                .nickname(post.getMember().getNickname())
                .build();
    }

    /**
     * 전체 게시글 조회
     * @return
     */
    public List<PostResponse.Summary> getAllPost() {
        List<Post> posts = postRepositoryPort.findAll();
        return posts.stream()
                .map(post -> PostResponse.Summary.builder()
                        .postId(post.getId())
                        .content(post.getContent())
                        .title(post.getTitle())
                        .nickname(post.getMember().getNickname())
                        .createdAt(post.getCreatedAt())
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
    public PostResponse.Detail updatePost(Long postId, Long memberId, PostRequest postRequestDTO) {
        String title = postRequestDTO.title();
        String content = postRequestDTO.content();

        Post post = postRepositoryPort.findById(postId);
        Member member = memberRepositoryPort.findById(memberId);

        if (!post.getMember().getId().equals(member.getId())) {
            throw new GlobalException(ErrorCode.NOT_MODIFIED_POST) {};
        }

        List<CommentResponse> commentList = post.getCommentList().stream()
                .map(CommentResponse::from)
                .toList();

        post.updatePost(title, content);
        postRepositoryPort.save(post);

        return PostResponse.Detail.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .nickname(post.getMember().getNickname())
                .comments(commentList)
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
