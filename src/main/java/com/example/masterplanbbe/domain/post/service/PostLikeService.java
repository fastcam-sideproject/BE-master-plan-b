package com.example.masterplanbbe.domain.post.service;

import com.example.masterplanbbe.domain.post.dto.PostResponse;
import com.example.masterplanbbe.domain.post.entity.PostLike;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.domain.post.repository.LikeRepositoryPort;
import com.example.masterplanbbe.domain.post.repository.PostRepositoryPort;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final MemberRepositoryPort memberRepositoryPort;
    private final PostRepositoryPort postRepositoryPort;
    private final LikeRepositoryPort likeRepositoryPort;

    @Transactional
    public PostResponse.Detail addLike(Long postId, Long memberId) {
        Member member = memberRepositoryPort.findById(memberId);
        Post post = postRepositoryPort.findById(postId);
        if (!likeRepositoryPort.existsByMemberAndPost(member, post)) {
            PostLike postLike = PostLike.builder()
                    .member(member)
                    .post(post)
                    .build();
            likeRepositoryPort.save(postLike);
            post.addLike();
            postRepositoryPort.save(post);
        }

        return PostResponse.Detail.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .nickname(post.getMember().getNickname())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }
}
