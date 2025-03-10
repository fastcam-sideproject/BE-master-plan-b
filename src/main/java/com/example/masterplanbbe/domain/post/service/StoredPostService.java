package com.example.masterplanbbe.domain.post.service;

import com.example.masterplanbbe.domain.post.dto.PostResponse;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.domain.post.entity.StoredPost;
import com.example.masterplanbbe.domain.post.repository.PostRepositoryAdapter;
import com.example.masterplanbbe.domain.post.repository.StoredPostRepositoryAdapter;
import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.member.repository.MemberRepositoryAdapter;
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

    /**
     * 게시글 북마크
     * @param memberId
     * @param postId
     * @return
     */
    @Transactional
    public PostResponse.Detail toggleStoredPost(String email, Long postId) {
        Member member = memberRepositoryAdapter.findByEmail(email);
        Post post = postRepositoryAdapter.findById(postId);

        if (storedPostRepositoryAdapter.existsByMemberAndPost(member, post)) {
            storedPostRepositoryAdapter.deleteByMemberAndPost(member,post);
        } else {
            storedPostRepositoryAdapter.save(new StoredPost(member, post));
        }

        return PostResponse.Detail.from(post);
    }

    /**
     * 북마크 게시글 조회
     * @param memberId
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<PostResponse.Summary> getStoredPost(String email, Pageable pageable) {
        Member member = memberRepositoryAdapter.findByEmail(email);
        Long memberId = member.getId();

        Page<StoredPost> posts = storedPostRepositoryAdapter.findByMemberId(memberId, pageable);
        return posts.map(s -> PostResponse.Summary.from(s.getPost()));
    }
}
