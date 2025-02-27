package com.example.masterplanbbe.domain.post.service;

import com.example.masterplanbbe.domain.post.dto.PostResponse;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.domain.post.entity.StoredPost;
import com.example.masterplanbbe.domain.post.repository.PostRepositoryAdapter;
import com.example.masterplanbbe.domain.post.repository.StoredPostRepositoryAdapter;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoredPostService {

    private final StoredPostRepositoryAdapter storedPostRepositoryAdapter;
    private final MemberRepositoryAdapter memberRepositoryAdapter;
    private final PostRepositoryAdapter postRepositoryAdapter;


    @Transactional
    public PostResponse.Detail toggleStoredPost(Long memberId, Long postId) {
        Member member = memberRepositoryAdapter.findById(memberId);
        Post post = postRepositoryAdapter.findById(postId);

        if (storedPostRepositoryAdapter.existsByMemberAndPost(member, post)) {
            storedPostRepositoryAdapter.deleteByMemberAndPost(member,post);
        } else {
            storedPostRepositoryAdapter.save(new StoredPost(member, post));
        }

        return PostResponse.Detail.from(post);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse.Summary> getStoredPost(Long memberId, Pageable pageable) {
        Page<StoredPost> posts = storedPostRepositoryAdapter.findByMemberId(memberId, pageable);
        return posts.map(s -> PostResponse.Summary.from(s.getPost()));
    }
}
